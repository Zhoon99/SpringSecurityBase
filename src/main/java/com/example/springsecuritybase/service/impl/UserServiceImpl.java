package com.example.springsecuritybase.service.impl;

import com.example.springsecuritybase.domain.Account;
import com.example.springsecuritybase.repository.UserRepository;
import com.example.springsecuritybase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void createUser(Account account) {
        userRepository.save(account);
    }
}
