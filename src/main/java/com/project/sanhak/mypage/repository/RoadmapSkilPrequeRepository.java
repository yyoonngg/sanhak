package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.skil.user.UserRoadmapSkil;
import com.project.sanhak.domain.skil.user.UserRoadmapSkilPreque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadmapSkilPrequeRepository extends JpaRepository<UserRoadmapSkilPreque, Integer> {

    List<UserRoadmapSkilPreque> findByURSPchildcsid(int childSkill);

    List<UserRoadmapSkilPreque> findByURSPparentscsid(int parentSkill);

    boolean existsByURSPparentscsidAndURSPchildcsid(int parent, int child);
}
