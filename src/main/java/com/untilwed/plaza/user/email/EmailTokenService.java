package com.untilwed.plaza.user.email;

import com.untilwed.plaza.user.repository.UserRepositoryImpl;
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
    private final UserRepositoryImpl userRepository;

    //이메일 인증 토큰 생성
    public String createEmailToken(Long number, String receiverEmail){
        log.info("[이메일토큰서비스] 이메일토큰을 생성합니다.");

        Assert.notNull(number, "memberId는 필수적입니다.");
        Assert.notNull(receiverEmail, "recevierEmail은 필수적입니다.");

        //이메일 토큰 저장
        EmailToken emailToken = EmailToken.createEmailToken(number);
        emailTokenRepository.save(emailToken);

        //이메일 전송
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("" +
                "아래에 링크를 클릭해주셔서 이메일인증을 마무리 해주세요."+
                "<br/>" +
                "http://localhost:8000/userlogin/confirm-email?token="+emailToken.getId() +
                "<br/>");
        emailSenderService.sendEmail(mailMessage);

        return emailToken.getId();
    }

    //유효한 토큰 가져오기
    public EmailToken findByIdAndExpirationDateAfterAndExpired(String emailTokenId) throws RuntimeException {
        Optional<EmailToken> emailToken = emailTokenRepository
                .findByIdAndExpirationDateAfterAndExpired(emailTokenId, LocalDateTime.now(), false);

        return emailToken.orElseThrow(() -> new RuntimeException());
    }

    public void resendEmailToken(String email){
        Long userNumber = userRepository.findNumberByEmail(email);
        createEmailToken(userNumber, email);
        log.info("재전송 완료 userNumber = {}, email = {} ", userNumber, email);
    }
}
