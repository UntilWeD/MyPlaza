package com.untilwed.plaza.user.controller;

import com.untilwed.plaza.user.User;
import com.untilwed.plaza.user.validation.UserValidator;
import com.untilwed.plaza.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController{

    private final UserValidator userValidator;

    @GetMapping("/change-user")
    @Override
    public String changeUserHome(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("user", loginUser);

        if (session == null){
            log.info("[유저컨트롤러]세션이 없는 사용자입니다.");
            return "/home";
        }

        return "/user/change-user";
    }

    /**
     * 유저 정보 변경 GetMapping + 유저의 세션정보를 받고 넘어가도록 한다. 만약 세션정보가 없으면 들어가지 못함
     * @param request
     * @return
     */
    @GetMapping("/change-userform")
    @Override
    public String changeUserFormHome(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("user", loginUser);

        if (session == null){
            log.info("[유저컨트롤러]세션이 없는 사용자입니다.");
            return "/home";
        }

        return "/user/change-userform";
    }

    /**
     * 유저 정보 변경 PostMapping
     * @param user
     * @return
     */
    @PostMapping("/change-userform")
    @Override
    public String changeUserForm(@ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request) {

        // 사용자의 요구에 따라 Validator를 UserChangeValidator로 따로 바꿔서 기능을 다르게 설정할 수 있다.
        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()){
            log.info("회원가입 중 오류가 발생하여 다시 되돌아갑니다 : {}", bindingResult);
            return "user/change-userform";
        }




        return "/user/change-user";
    }
}
