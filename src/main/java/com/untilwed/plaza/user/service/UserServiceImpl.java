package com.untilwed.plaza.user.service;

import com.untilwed.plaza.user.DeleteUserForm;
import com.untilwed.plaza.user.LoginForm;
import com.untilwed.plaza.user.User;
import com.untilwed.plaza.user.UserFindPwForm;
import com.untilwed.plaza.user.email.EmailSenderService;
import com.untilwed.plaza.user.email.EmailService;
import com.untilwed.plaza.user.email.EmailTokenService;
import com.untilwed.plaza.user.repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepositoryImpl userRepository;
    private final EmailTokenService emailTokenService;
    private final EmailSenderService emailSenderService;

    private long userNumber = 0;

    @Override
    public User register(User user) {
        log.info("register user = {}", user);

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
            log.info("해당 데이터 베이스에 없는 사용자 id입니다. ");
            return null;
        }

        log.info("loginUser : {}", loginUser);

        if(loginUser.get().getPassword().equals(loginForm.getPassword())){
            if(loginUser.get().isEmailverified()){
                log.info("로그인에 성공하셧습니다.");
                return loginUser.get();
            }else {
                log.info("이메일인증이 완료되지 않은 사용자입니다.");
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
            log.info("해당 유저는 존재하지 않습니다.");
            return null;
        }
        return findUser;
    }

    @Override
    public User deleteUser(User user) {
        log.info("deleteUser를 실행합니다.");

        return userRepository.deleteUser(user);
    }

    // 유저의 유저정보변경
    @Override
    public User changeUser(User user, Long userNumber) {
        log.info("changeUser를 실행합니다.");
        User updatedUser = userRepository.updateUser(user, userNumber);


        return updatedUser;
    }

    @Override
    public String findUserIdByEmail(String email) {
        log.info("findUserIdByEmail 메서드를 실행중입니다.");

        //유저 리포지토리 필요
        User findUser = userRepository.findUserByEmail(email).get();

        //나중에 없는 사용자라고 넣어도 될듯?
        //이메일 전송 서비스
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        try{
            mailMessage.setTo(email);
            mailMessage.setSubject("회원님의 Id");
            mailMessage.setText("" +
                    "회원님의 아이디는 다음과 같습니다. \n" +
                    "\n" +
                    findUser.getId()
                    );
            emailSenderService.sendEmail(mailMessage);
        } catch (Exception e){
            log.info("Exception = {}",e);

        }

        return findUser.getId();
    }

    @Override
    public void findUserPwByIdAndEmail(UserFindPwForm pwForm) {
        log.info("findUserPwByIdAndEmail메서드가 실행중입니다.");

        String email = pwForm.getEmail();
        User findUser = userRepository.findUserByEmailAndId(pwForm.getEmail(), pwForm.getId()).get();

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        try{
            mailMessage.setTo(email);
            mailMessage.setSubject("회원님의 Password");
            mailMessage.setText("" +
                    "회원님의 아이디는 다음과 같습니다. \n" +
                    "\n" +
                    findUser.getId()
            );
        }catch (Exception e){
            log.info("Exception={}", e);
        }

        return null;
    }
}
