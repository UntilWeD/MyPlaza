package com.untilwed.plaza.user.repository;

import com.untilwed.plaza.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {



    // 유저 수정
    public User saveUser(User user);
    public User updateUser(User user, Long userNumber);
    public User deleteUser(User user);

    // 유저 검색
    public Optional<User> findUser(User user);
    public Optional<User> findByIdUser(String id);
    public List<User> findAllUser();
    public long findNumberByEmail(String email);
    public Optional<User> findUserByEmail(String email);

    //다른기능
    public Optional<User> setEmailVerifiedByNumber(Long userNumber);

}
