package com.untilwed.plaza.user.controller;

import com.untilwed.plaza.user.LoginForm;
import com.untilwed.plaza.user.User;
import com.untilwed.plaza.user.service.UserServiceImpl;
import com.untilwed.plaza.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/userlogin")
@Controller
@RequiredArgsConstructor
public class UserLoginControllerImpl implements UserLoginController {
    private final UserServiceImpl userService;



    //클라이언트가 회원가입버튼을 눌렀을 시에
    @GetMapping("/register")
    public String userRegisterhome(@ModelAttribute("user") User user){
        return "user/userlogin/register";
    }

    //클라이언트가 회원정보를 기입하고 가입완료버튼을 눌렀을 시에
    @PostMapping("/register")
    @Override
    public String userRegister(@ModelAttribute User user, Model model) {
        log.info("Generated user = {}", user);

        User savedUser = userService.register(user);

        model.addAttribute("user", savedUser);


        return "redirect:/userlogin/home";
    }

    //처음 로그인 화면
    @GetMapping("/home")
    public String userLoginHome(@ModelAttribute("user") User user){
        log.info("로그인 홈 메서드 실행");
        return "user/userlogin/home";
    }

    //TODO
    //암호화된쿠키와 세션을 사용하여 로그인기능을 만든다.
    @Override
    @PostMapping("/home")
    public String userLogin(@ModelAttribute LoginForm loginForm, Model model, HttpServletRequest request) {
        log.info("로그인 컨트롤러 메서드 실행 loginForm : {}", loginForm);

        User loginUser = userService.login(loginForm);

        //로그인 성공처리
        //세션이 있으면 있는 세션 반환, 없으면 신규세션을 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 정보 보관
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);

        return "redirect:/";
    }

    //TODO
    //쿠키와 세션을 사용하여 로그아웃기능을 만든다.
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
}
