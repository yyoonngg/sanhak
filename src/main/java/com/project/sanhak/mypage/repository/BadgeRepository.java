package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.user.Badge;
import com.project.sanhak.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Integer> {
    int countByUBUid(User user);

    List<Badge> findByUBUid(User user);

    List<Badge> findByUBUid_UId(Integer uid);
}
