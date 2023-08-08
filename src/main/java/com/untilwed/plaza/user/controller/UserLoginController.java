package com.untilwed.plaza.user.controller;

import com.untilwed.plaza.user.LoginForm;
import com.untilwed.plaza.user.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

public interface UserLoginController {

    public String userRegister(User user, Model model);
    public String userLogin(LoginForm loginForm, Model model, HttpServletRequest request);
    public String userLogout(HttpServletRequest request);


}
