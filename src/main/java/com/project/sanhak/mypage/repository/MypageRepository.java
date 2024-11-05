package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.user.User;
import com.project.sanhak.domain.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MypageRepository extends JpaRepository<UserInfo, Integer> {
}
