package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.skil.user.UserRoadmap;
import com.project.sanhak.domain.skil.user.UserRoadmapSkilPreque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadmapSkilPrequeRepository extends JpaRepository<UserRoadmapSkilPreque, Integer> {
    void deleteByURSPparentscsidAndURSPchildcsid(int parentId, int childId);
}
