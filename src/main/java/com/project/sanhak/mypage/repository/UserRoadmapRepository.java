package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.skil.user.UserRoadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoadmapRepository extends JpaRepository<UserRoadmap, Integer> {
    // Additional query methods if needed
}
