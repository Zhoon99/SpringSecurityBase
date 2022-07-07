package com.example.springsecuritybase.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache(); //요청 캐시 관련 작업
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        setDefaultTargetUrl("/");

        SavedRequest savedRequest = requestCache.getRequest(request, response); //사용자가 인증 전에 이동하고자 했던 페이지의 URL 정보 저장
        if (savedRequest != null) { //savedRequest 는 null 일 수도 있음 (ex 바로 로그인 요청을 할 때, 다른 자원에 접근했다가 인증예외가 발생해서 로그인 페이지로 이동됐을 때)
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, targetUrl); //인증 성공 후 이동
        } else {
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl()); //인증 실패 시 루트 페이지로 이동
        }
    }
}
