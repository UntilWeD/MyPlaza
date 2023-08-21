package com.untilwed.plaza.user.service;

import com.untilwed.plaza.user.LoginForm;
import com.untilwed.plaza.user.User;
import com.untilwed.plaza.user.email.EmailTokenService;
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
    private final EmailTokenService emailTokenService;

    private long userNumber = 0;

    @Override
    public User register(User user) {
        log.info("serviceUser = {}", user);

        log.info("[유저서비스]이메일을 전송하였습니다.");

        User savedUser = userRepository.saveUser(user);

        emailTokenService.createEmailToken(userRepository.findNumberByEmail(user.getEmail()), user.getEmail());


        return savedUser;
    }

    @Override
    public User login(LoginForm loginForm) {
        Optional<User> loginUser = userRepository.findByIdUser(loginForm.getId());

        //TODO
        //Spring에서 배운거 써먹기 (validator, error) 사용하기
        if(loginUser == null){
            log.info("[유저서비스]해당 데이터 베이스에 없는 사용자 id입니다. ");
            return null;
        }

        log.info("loginUser : {}", loginUser);

        if(loginUser.get().getPassword().equals(loginForm.getPassword())){
            if(loginUser.get().isEmailverified()){
                log.info("[유저서비스]로그인에 성공하셧습니다.");
                return loginUser.get();
            }else {
                log.info("[유저서비스]이메일인증이 완료되지 않은 사용자입니다.");
                return loginUser.get();
            }

        } else {
          log.info("로그인에 실패하셨습니다. 아이디나 비밀번호를 다시 확인해주세요.");
          return null;
        }
    }

    @Override
    public void logout(User user) {

    }

    @Override
    public Optional<User> setEmailVerify(Long userNumber) {
        log.info("UserServiceImpl의 setEmailVerify메서드를 실행하겠습니다.");
        Optional<User> findUser = userRepository.setEmailVerifiedByNumber(userNumber);

        if(findUser.isEmpty()){
            log.info("[유저서비스]해당 유저는 존재하지 않습니다.");
            return null;
        }
        return findUser;
    }

    @Override
    public String findUserIdByEmail(String email) {
        log.info("[유저서비스]findUserIdByEmail 메서드를 실행중입니다.");

        //유저 리포지토리 필요
        User findUser = userRepository.findUserByEmail(email).get();

        return findUser.getId();
    }
}
