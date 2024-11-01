// UserRoadmapService.java
package com.project.sanhak.mypage.service;

import com.project.sanhak.domain.user.User;
import com.project.sanhak.domain.skil.user.UserRoadmap;
import com.project.sanhak.mypage.dto.UserRoadmapDTO;
import com.project.sanhak.mypage.repository.UserRoadmapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoadmapService {

    @Autowired
    private UserRoadmapRepository userRoadmapRepository;

    public UserRoadmap createUserRoadmap(UserRoadmapDTO userRoadmapDTO, User user) {
        // DTO의 toEntity 메서드를 사용하여 UserRoadmap 엔티티 생성
        UserRoadmap userRoadmap = userRoadmapDTO.toEntity(user);
        return userRoadmapRepository.save(userRoadmap);
    }

    public UserRoadmap findById(int id) {
        return userRoadmapRepository.findById(id).orElse(null); // 예외 대신 null 반환
    }
}
