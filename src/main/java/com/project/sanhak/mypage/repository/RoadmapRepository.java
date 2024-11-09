package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.skil.user.UserRoadmap;
import com.project.sanhak.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoadmapRepository extends JpaRepository<UserRoadmap, Integer> {
    List<UserRoadmap> findByURuid_UId(int uid);
    UserRoadmap findByURIdAndURuid(int urId, User user);
}
