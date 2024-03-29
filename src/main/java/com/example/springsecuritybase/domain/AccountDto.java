package com.example.springsecuritybase.domain;

import lombok.Data;

@Data
public class AccountDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String age;
    private String role;
}
