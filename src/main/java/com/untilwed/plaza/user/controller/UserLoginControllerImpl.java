package com.untilwed.plaza.user.controller;

import com.untilwed.plaza.user.LoginForm;
import com.untilwed.plaza.user.User;
import com.untilwed.plaza.user.service.UserServiceImpl;
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
        return "user/userlogin/home";
    }

    //TODO
    //후에
    //암호화된쿠키와 세션을 사용하여 로그인기능을 만든다.
    @Override
    @PostMapping("/home")
    public String userLogin(@ModelAttribute LoginForm loginForm, Model model) {
        log.info("로그인 컨트롤러 메서드 실행 loginForm : {}", loginForm);

        User loggedinUser = userService.login(loginForm);
        model.addAttribute("user", loggedinUser);

        return "redirect:/";
    }

    //TODO
    //쿠키와 세션을 사용하여 로그아웃기능을 만든다.
    @Override
    public String userLogout(User user) {
        return null;
    }
}
