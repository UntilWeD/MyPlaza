package com.untilwed.plaza.user.controller;

import com.untilwed.plaza.user.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserController {
    public String changeUserHome(HttpServletRequest request, Model model);
    public String changeUserFormHome(HttpServletRequest request, Model model);
    public String changeUserForm(@ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request);
}
