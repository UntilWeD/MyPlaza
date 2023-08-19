package com.untilwed.plaza.user.email;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailTokenService {

    private final EmailSenderService emailSenderService;
    private final EmailTokenRepository emailTokenRepository;

    //이메일 인증 토큰 생성
    public String createEmailToken(Long number, String receiverEmail){

        Assert.notNull(number, "memberId는 필수적입니다.");
        Assert.notNull(receiverEmail, "recevierEmail은 필수적입니다.");

        //이메일 토큰 저장
        EmailToken emailToken = EmailToken.createEmailToken(number);
        emailTokenRepository.save(emailToken);

        //이메일 전송
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("\"http://localhost:8000/confirm-email?token=\"+emailToken.getId()");
        emailSenderService.sendEmail(mailMessage);

        return emailToken.getId();
    }

    //유효한 토큰 가져오기
    public EmailToken findByIdAndExpirationDateAfterAndExpired(String emailTokenId) throws RuntimeException {
        Optional<EmailToken> emailToken = emailTokenRepository
                .findByIdAndExpirationDateAfterAndExpired(emailTokenId, LocalDateTime.now(), false);

        return emailToken.orElseThrow(() -> new RuntimeException());
    }
}