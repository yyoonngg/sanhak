package com.project.sanhak.login.repository;

import com.project.sanhak.domain.user.OAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<OAuthToken, Integer> {
    Optional<OAuthToken> findUserByEmailAndProvider(String email, String provider);

    String findByEmail(String uEmailId);
}
