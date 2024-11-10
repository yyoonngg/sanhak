package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.user.Badge;
import com.project.sanhak.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Integer> {
    int countByUBUid(User user);
}