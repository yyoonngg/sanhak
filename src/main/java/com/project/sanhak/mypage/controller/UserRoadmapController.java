// UserRoadmapController.java
package com.project.sanhak.mypage.controller;

import com.project.sanhak.domain.user.User;
import com.project.sanhak.domain.skil.user.UserRoadmap;
import com.project.sanhak.mypage.dto.UserRoadmapDTO;
import com.project.sanhak.mypage.service.UserRoadmapService;
import com.project.sanhak.main.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-roadmaps")
public class UserRoadmapController {

    @Autowired
    private UserRoadmapService userRoadmapService;

    @Autowired
    private MainService mainService;

    @PostMapping("/create")
    public ResponseEntity<UserRoadmap> createUserRoadmap(@RequestBody UserRoadmapDTO userRoadmapDTO) {
        User currentUser = mainService.getCurrentUser(); // 현재 사용자 정보 가져오기
        UserRoadmap userRoadmap = userRoadmapService.createUserRoadmap(userRoadmapDTO, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRoadmap);
    }
}
