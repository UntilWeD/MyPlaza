package com.untilwed.plaza.user.service;

import com.untilwed.plaza.user.LoginForm;
import com.untilwed.plaza.user.User;

import java.util.Optional;

public interface UserService {

    public User register(User user);
    public User login(LoginForm loginForm);
    public void logout(User user);

    public Optional<User> setEmailVerify(Long userNumber);
    public String findUserIdByEmail(String email);

    //TODO: 이메일 비밀번호찾기는 회원정보수정을 만든후에 하도록 하자 (URL을 비밀번호변경으로 보내기)
}
