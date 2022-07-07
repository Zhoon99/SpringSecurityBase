package com.example.springsecuritybase.controller.login;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "user/login/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //SecurityContext 에서 인증객체를 가져옴

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth); //GET 방식의 로그아웃은 SecurityContextLogoutHandler 사용
        }
        return "redirect:/login";
    }
}
