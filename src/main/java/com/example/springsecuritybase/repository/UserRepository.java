package com.example.springsecuritybase.repository;

import com.example.springsecuritybase.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {
}
