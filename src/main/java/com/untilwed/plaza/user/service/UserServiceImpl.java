package com.untilwed.plaza.user.service;

import com.untilwed.plaza.user.User;
import com.untilwed.plaza.user.repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepositoryImpl userRepository;

    private long userNumber = 0;

    @Override
    public User register(User user) {
        log.info("serviceUser = {}", user);

        User savedUser = userRepository.saveUser(user);
        return savedUser;
    }

    @Override
    public User login(User user) {
        return null;
    }

    @Override
    public void logout(User user) {

    }
}
