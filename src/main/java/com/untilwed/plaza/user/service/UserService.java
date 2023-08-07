package com.untilwed.plaza.user.service;

import com.untilwed.plaza.user.User;

import java.util.Optional;

public interface UserService {

    public User register(User user);
    public User login(User user);
    public void logout(User user);
}
