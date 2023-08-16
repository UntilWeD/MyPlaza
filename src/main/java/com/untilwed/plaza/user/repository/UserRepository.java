package com.untilwed.plaza.user.repository;

import com.untilwed.plaza.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    public User saveUser(User user);

    public Optional<User> findUser(User user);
    public Optional<User> findByIdUser(String id);
    public List<User> findAllUser();

    public Optional<User> deleteUser(User user);

    public Optional<User> setEmailVerifiedByNumber(Long userNumber);
}
