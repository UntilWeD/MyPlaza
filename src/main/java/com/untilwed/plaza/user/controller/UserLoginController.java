package com.untilwed.plaza.user.controller;

import com.untilwed.plaza.user.LoginForm;
import com.untilwed.plaza.user.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface UserLoginController {

    public String userRegister(User user, BindingResult bindingResult, Model model);
    public String userLogin(LoginForm loginForm, BindingResult bindingResult,Model model, HttpServletRequest request);
    public String userLogout(HttpServletRequest request);


}
