package com.untilwed.plaza.user.controller;

import com.untilwed.plaza.user.LoginForm;
import com.untilwed.plaza.user.User;
import org.springframework.ui.Model;

public interface UserLoginController {

    public String userRegister(User user, Model model);
    public String userLogin(LoginForm loginForm, Model model);
    public String userLogout(User user);


}
