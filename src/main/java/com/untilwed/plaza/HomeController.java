package com.untilwed.plaza;

import com.untilwed.plaza.user.User;
import com.untilwed.plaza.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model){
        log.info("홈 컨트롤러 메서드 실행");

        HttpSession session = request.getSession(false);
        if(session == null){
            return "home";
        }

        //세션에 데이터가 없으면 home
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        if(loginUser == null){
            return "home";
        }

        log.info("loginhome에 전달되는 user 객체 정보: {}", loginUser);

        model.addAttribute("user", loginUser);
        return "/loginhome";
    }
}
