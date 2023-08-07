package com.untilwed.plaza.user.service;

import com.untilwed.plaza.user.LoginForm;
import com.untilwed.plaza.user.User;

import java.util.Optional;

public interface UserService {

    public User register(User user);
    public User login(LoginForm loginForm);
    public void logout(User user);
}
