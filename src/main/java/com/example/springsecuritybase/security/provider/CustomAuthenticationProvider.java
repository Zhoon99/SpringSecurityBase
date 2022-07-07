package com.example.springsecuritybase.security.provider;

import com.example.springsecuritybase.security.service.AccountContext;
import com.example.springsecuritybase.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

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

        //인증 객체의 정보를 담은 토큰 생성 -> AuthenticationManager 에게 전달
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails.getAccount(), null, userDetails.getAuthorities());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication); //토큰이 파라미터로 전달된 클래스 타입과 일치할 때 인증 처리
    }
}
