package com.untilwed.plaza.user.controller;

import com.untilwed.plaza.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/userlogin")
@Controller
public class UserLoginLoginControllerImpl implements UserLoginController {


    //클라이언트가 회원가입버튼을 눌렀을 시에
    @GetMapping("/register")
    public String userRegisterhome(){
        return "user/userlogin/register";
    }

    //클라이언트가 회원정보를 기입하고 가입완료버튼을 눌렀을 시에
    @PostMapping("/register")
    @Override
    public String userRegister(@ModelAttribute User user) {



        return "/userlogin/";
    }

    //처음 로그인 화면
    @GetMapping("/home")
    public String userLoginHome(){
        log.info("userLoginHome 메서드 실행");
        return "user/userlogin/home";
    }

    //TODO
    //암호화된쿠키와 세션을 사용하여 로그인기능을 만든다.
    @Override
    public String userLogin(User user) {
        return null;
    }

    //TODO
    //쿠키와 세션을 사용하여 로그아웃기능을 만든다.
    @Override
    public String userLogout(User user) {
        return null;
    }
}
