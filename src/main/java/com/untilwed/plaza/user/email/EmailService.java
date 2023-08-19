package com.untilwed.plaza.user.email;

import com.untilwed.plaza.user.User;
import com.untilwed.plaza.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 무슨 어노테이션?? 찾아보자
public class EmailService {

    private final EmailTokenService emailTokenService;
    private final UserServiceImpl userService;


    @Transactional
    public boolean verifyEmail(String token) throws RuntimeException{

        //이메일 토큰을 찾아온다
        EmailToken findEmailToken = emailTokenService.findByIdAndExpirationDateAfterAndExpired(token);

        //고유 유저 넘버 찾기
        Long userNumber = findEmailToken.getUserNumber();
        Optional<User> findUser = userService.setEmailVerify(userNumber);

        // 지금 예시는 유저의 인증내용 변경하는 방법입니다.
        findEmailToken.setTokenToUsed(); // 사용완료

        if(findUser.isPresent()){
            log.info("유저가 존재합니다. 유저 : {}", findUser.get());
            return true;
        }
        log.info("해당유저는 데이터베이스에 존재하지 않습닌다.");
        return false;
    }

}
