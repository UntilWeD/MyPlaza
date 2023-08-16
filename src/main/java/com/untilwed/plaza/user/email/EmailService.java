package com.untilwed.plaza.user.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 무슨 어노테이션?? 찾아보자
public class EmailService {

    private final EmailTokenService emailTokenService;


    @Transactional
    public boolean verifyEmail(String token) throws RuntimeException{
        //이메일 토큰을 찾아온다
        EmailToken findEmailToken = emailTokenService.findByIdAndExpirationDateAfterAndExpired(token);

        // TODO: 여기서부터는 이메일 성공 인증 로직을 구현합니다.
        // 지금 예시는 유저의 인증내용 변경하는 방법입니다.
        return true;

    }

}
