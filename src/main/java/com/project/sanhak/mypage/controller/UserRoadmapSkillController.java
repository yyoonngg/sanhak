// UserRoadmapSkillController.java
package com.project.sanhak.mypage.controller;

import com.project.sanhak.domain.skil.user.UserRoadmapSkil;
import com.project.sanhak.mypage.dto.UserRoadmapSkillDTO;
import com.project.sanhak.mypage.service.UserRoadmapSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-roadmap-skills")
public class UserRoadmapSkillController {

    @Autowired
    private UserRoadmapSkillService userRoadmapSkillService;

    @PostMapping("/create")
    public ResponseEntity<UserRoadmapSkil> createUserRoadmapSkill(@RequestBody UserRoadmapSkillDTO skillDTO) {
        UserRoadmapSkil userRoadmapSkill = userRoadmapSkillService.createUserRoadmapSkill(skillDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRoadmapSkill);
    }
}
