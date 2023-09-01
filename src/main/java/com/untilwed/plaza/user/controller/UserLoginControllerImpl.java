package com.untilwed.plaza.user.controller;

import com.untilwed.plaza.user.LoginForm;
import com.untilwed.plaza.user.User;
import com.untilwed.plaza.user.UserFindIdForm;
import com.untilwed.plaza.user.UserFindPwForm;
import com.untilwed.plaza.user.service.UserServiceImpl;
import com.untilwed.plaza.user.validation.UserValidator;
import com.untilwed.plaza.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/userlogin")
@Controller
@RequiredArgsConstructor
public class UserLoginControllerImpl implements UserLoginController {
    private final UserServiceImpl userService;
    private final UserValidator userValidator;

    //클라이언트가 회원가입버튼을 눌렀을 시에
    @GetMapping("/register")
    public String userRegisterhome(@ModelAttribute("user") User user){
        return "user/userlogin/register";
    }

    //클라이언트가 회원정보를 기입하고 가입완료버튼을 눌렀을 시에
    @PostMapping("/register")
    @Override
    public String userRegister(@Validated @ModelAttribute User user, BindingResult bindingResult, Model model) {
        log.info("Generated user = {}", user);

        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()){
            log.info("회원가입 중 오류가 발생하여 다시 되돌아갑니다 : {}", bindingResult);
            return "user/userlogin/register";
        }

        User savedUser = userService.register(user);

        model.addAttribute("user", savedUser);

        return "/user/userlogin/email-confirm";
    }

    //처음 로그인 화면
    @GetMapping("/loginhome")
    public String userLoginHome(@ModelAttribute("loginForm") LoginForm loginForm){
        log.info("로그인 홈 메서드 실행");
        return "/user/userlogin/loginhome";
    }

    @Override
    @PostMapping("/loginhome")
    public String userLogin(@ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, Model model, HttpServletRequest request) {
        log.info("로그인 컨트롤러 메서드 실행 loginForm : {}", loginForm);

        User loginUser = userService.login(loginForm);


        if(loginUser == null){
            log.info("로그인 중 오류가 발생하여 다시 되돌아갑니다 : {}", bindingResult);

            bindingResult.reject("loginError");
            return "user/userlogin/loginhome";
        } else if(!(loginUser.isEmailverified())){

            log.info("이메일인증이 완료되지 않은 사용자입니다.");
            bindingResult.reject("emailNotVerified");
            return "user/userlogin/loginhome";
        }


        //로그인 성공처리
        //세션이 있으면 있는 세션 반환, 없으면 신규세션을 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 정보 보관
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);



        return "redirect:/";
    }


    // 로그아웃 기능
    @Override
    @PostMapping ("/logout")
    public String userLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null){
            // 세션 삭제
            session.invalidate();
        }

        return "redirect:/";
    }

    //아이디찾기
    @GetMapping("/find-id")
    public String findIdByEmailHome(@ModelAttribute("userFindForm") UserFindIdForm userFindIdForm){
        log.info("[findIdByEmailHome] 메서드 실행");
        return "/user/userlogin/find-id";
    }

    //아이디찾기
    @PostMapping("/find-id")
    public String findIdByEmail(@ModelAttribute("userFindForm") UserFindIdForm userFindIdForm, BindingResult bindingResult, Model model){
        log.info("사용자로 부터 받아온 email입니다. email: {}", userFindIdForm);

        String email = userFindIdForm.getEmail();

        if(email.equals("") || !(email.contains("@")) || !(email.contains(".com")) ){

            bindingResult.reject("find-id");
            return "/user/userlogin/find-id";
        }

        userService.findUserIdByEmail(email);

        model.addAttribute("email", email);
        return "/user/userlogin/find-id-finished";
    }

    //비밀번호찾기
    @GetMapping("/find-password")
    public String findPwByPwFindFormHome(@ModelAttribute("pwForm") UserFindPwForm pwForm){
        return "/user/userlogin/find-pw";
    }

    @PostMapping("/find-password")
    public String findPwByPwFindForm(@ModelAttribute("pwForm") UserFindPwForm pwForm, BindingResult bindingResult, Model model){
        log.info("findPwByPwFindForm 메서드가 실행되고 있습니다.");

        if(bindingResult.hasErrors()){
            log.info("findPwByPwFindForm메서드 실행중 오류 발생");
            return "/user/userlogin/find-pw";
        }

        model.addAttribute("email", pwForm.getEmail());
        return "/user/userlogin/find-pw-finished";
    }


}

//    //해당 컨트롤러에만 영향
//    @InitBinder
//    public void init(WebDataBinder dataBinder){
//        dataBinder.addValidators(userValidator);
//    }

//        //검증로직
//        if(user.getUsername().length() > 10 || user.getUsername().length() <= 0){
//            bindingResult.addError(new FieldError("user", "username", user.getUsername() ,false, null, null, "유저이름은 0보다 크고 10보다 작아야합니다."));
//        }
//
//        //id
//        if(user.getId().length() >= 10 || user.getId().length() <= 1){
//            bindingResult.addError(new FieldError("user", "id", user.getId(), false, new String[]{"length.user.username"}, new Object[]{10}, null));
//        }
//
//        //pasword
//        if(user.getPassword().length() > 20 || user.getPassword().length()  < 6){
//            bindingResult.rejectValue("password", "length");
//        }
//
//        if(user.getUsername() == "" || user.getId() == "" || user.getPassword() == "" ||
//                user.getEmail() == "" ){
//            bindingResult.addError(new ObjectError("user", null, null, "비어 있는 칸이 있습니다."));
//        }