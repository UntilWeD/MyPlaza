package com.untilwed.plaza.user.controller;

import org.springframework.stereotype.Controller;

//유저와 이메일과 관련된 컨트롤러이다.
@Controller
public interface UserEmailController {

    public String findPwByEmail(String email);



}
