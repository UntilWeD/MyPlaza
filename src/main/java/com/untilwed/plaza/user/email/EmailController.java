package com.untilwed.plaza.user.email;

import com.untilwed.plaza.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RequiredArgsConstructor
@Controller
@RequestMapping("/userlogin")
public class EmailController {

    private final EmailService emailService;
    private final UserServiceImpl userService;
    private final EmailTokenService emailTokenService;

    //사용자가 이메일을 받고서 내용의 이미지를 클릭했을때
    @GetMapping("/confirm-email")
    @ResponseBody
    public ResponseEntity viewConfirmEmail(@Valid @RequestParam String token){
        try{
            //이메일 인증 로직을 실행합니다.
            log.info("token = {}", token);

            boolean result = emailService.verifyEmail(token);
            log.info("result = {}", result);


            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 사용자 이메일 재전송 홈
    @GetMapping ("/resend-email")
    public String resendEmail(){
        return "/user/userlogin/resend-email";
    }

    // 사용자의 이메일인증 재전송
    @PostMapping("/resend-email")
    public String resendEmail(@Param("email") String email){
        log.info("[이메일컨트롤러]사용자의 이메일 재전송 요청입니다.");

        emailTokenService.resendEmailToken(email);

        return "/user/userlogin/loginhome";
    }

}
