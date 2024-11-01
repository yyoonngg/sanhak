package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.skil.user.UserRoadmapSkil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoadmapSkillRepository extends JpaRepository<UserRoadmapSkil, Integer> {
    // 필요한 추가 쿼리 메서드
}