package com.untilwed.plaza.user.controller;

import com.untilwed.plaza.user.User;

public interface UserLoginController {

    public String userRegister(User user);
    public String userLogin(User user);
    public String userLogout(User user);


}
