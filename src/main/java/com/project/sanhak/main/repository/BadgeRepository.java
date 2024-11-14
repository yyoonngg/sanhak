package com.project.sanhak.main.repository;

import com.project.sanhak.domain.user.Badge;
import com.project.sanhak.domain.user.OAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Integer> {
}
