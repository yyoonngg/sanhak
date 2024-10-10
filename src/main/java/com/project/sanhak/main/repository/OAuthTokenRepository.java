package com.project.sanhak.main.repository;

import com.project.sanhak.domain.user.OAuthToken;
import com.project.sanhak.domain.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthTokenRepository extends JpaRepository<OAuthToken, Integer> {
}
