package com.untilwed.plaza.user.repository;

import com.untilwed.plaza.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    public Optional<User> saveUser(User user);

    public Optional<User> findUser(User user);
    public Optional<User> findByIdUser(User user);
    public List<User> findAllUser();

    public Optional<User> deleteUser(User user);
}
