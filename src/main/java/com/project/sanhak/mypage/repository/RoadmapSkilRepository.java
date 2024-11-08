package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.skil.user.UserRoadmap;
import com.project.sanhak.domain.skil.user.UserRoadmapSkil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadmapSkilRepository extends JpaRepository<UserRoadmapSkil, Integer> {
}
