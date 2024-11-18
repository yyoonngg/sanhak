package com.project.sanhak.main.repository;

import com.project.sanhak.domain.user.OAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthTokenRepository extends JpaRepository<OAuthToken, Integer> {

    OAuthToken findByEmail(String uEmailId);
}
