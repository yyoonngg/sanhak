package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.skil.user.UserRoadmap;
import com.project.sanhak.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadmapRepository extends JpaRepository<UserRoadmap, Integer> {
    List<UserRoadmap> findByURuid_UId(int uid);
    UserRoadmap findByURIdAndURuid(int urId, User user);

    int countByURuid(User user);

    List<UserRoadmap> findByURuid_UIdAndState(int uid, int i);
}
