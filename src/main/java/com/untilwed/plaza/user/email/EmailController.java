package com.untilwed.plaza.user.email;

import com.untilwed.plaza.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class EmailController {

    private final EmailService emailService;

    //사용자가 이메일을 받고서 내용의 이미지를 클릭했을때
    @GetMapping("/confirm-email")
    public ResponseEntity viewConfirmEmail(@Valid @RequestParam String token){
        try{
            //이메일 인증 로직을 실행합니다.

            boolean result = emailService.verifyEmail(token);
            log.info("result = {}", result);


            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
