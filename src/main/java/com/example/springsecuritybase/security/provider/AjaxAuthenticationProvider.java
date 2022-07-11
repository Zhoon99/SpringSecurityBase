package com.example.springsecuritybase.security.provider;

import com.example.springsecuritybase.security.common.FormWebAuthenticationDetails;
import com.example.springsecuritybase.security.service.AccountContext;
import com.example.springsecuritybase.security.service.CustomUserDetailsService;
import com.example.springsecuritybase.security.token.AjaxAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AjaxAuthenticationProvider implements AuthenticationProvider { //폼 인증 방식의 provider 와 동일

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //입력 값
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        //DB 값
        AccountContext userDetails = (AccountContext) customUserDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, userDetails.getAccount().getPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }

        //인증 검증이 완료되면 AjaxAuthenticationToken 을 생성하여 최종 인증 객체를 반환
        AjaxAuthenticationToken authenticationToken =
                new AjaxAuthenticationToken(userDetails.getAccount(), null, userDetails.getAuthorities());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) { //ProviderManager 로 부터 넘어온 인증객체가 AjaxAuthenticationToken 타입이면 최종 인증 객체 반환
        return AjaxAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
