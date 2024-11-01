// UserRoadmapSkillService.java
package com.project.sanhak.mypage.service;

import com.project.sanhak.domain.skil.code.CodeSkil;
import com.project.sanhak.domain.skil.user.UserRoadmap;
import com.project.sanhak.domain.skil.user.UserRoadmapSkil;
import com.project.sanhak.mypage.dto.UserRoadmapSkillDTO;
import com.project.sanhak.mypage.repository.CodeSkillRepository;
import com.project.sanhak.mypage.repository.UserRoadmapRepository;
import com.project.sanhak.mypage.repository.UserRoadmapSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoadmapSkillService {

    @Autowired
    private UserRoadmapRepository userRoadmapRepository;

    @Autowired
    private CodeSkillRepository codeSkillRepository;

    @Autowired
    private UserRoadmapSkillRepository userRoadmapSkillRepository;

    public UserRoadmapSkil createUserRoadmapSkill(UserRoadmapSkillDTO skillDTO) {
        // 1. 로드맵 이름으로 UserRoadmap 조회
        UserRoadmap userRoadmap = userRoadmapRepository.findById(skillDTO.getUserRoadmapId())
                .orElseThrow(() -> new IllegalArgumentException("해당 로드맵을 찾을 수 없습니다. ID: " + skillDTO.getUserRoadmapId()));

        // 2. 스킬 이름으로 CodeSkil 조회
        CodeSkil codeSkil = codeSkillRepository.findByCSName(skillDTO.getSkillName())
                .orElseThrow(() -> new IllegalArgumentException("해당 스킬을 찾을 수 없습니다. 스킬 이름: " + skillDTO.getSkillName()));

        // 3. DTO를 사용하여 UserRoadmapSkill 엔티티 생성 및 매핑
        UserRoadmapSkil userRoadmapSkill = skillDTO.toEntity(userRoadmap, codeSkil);

        // 4. UserRoadmapSkil 엔티티 저장
        return userRoadmapSkillRepository.save(userRoadmapSkill);
    }
}
