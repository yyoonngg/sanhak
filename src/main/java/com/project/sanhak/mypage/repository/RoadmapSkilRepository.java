package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.skil.user.UserRoadmap;
import com.project.sanhak.domain.skil.user.UserRoadmapSkil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadmapSkilRepository extends JpaRepository<UserRoadmapSkil, Integer> {
    List<UserRoadmapSkil> findByURSurid(UserRoadmap userRoadmap);
}
