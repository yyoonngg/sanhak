package com.project.sanhak.login.repository;

import com.project.sanhak.domain.user.OAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;  // 추가 필요

public interface LoginUserRepository extends JpaRepository<OAuthToken, Integer> {
    Optional<OAuthToken> findUserByEmailAndProvider(String email, String provider);
}
