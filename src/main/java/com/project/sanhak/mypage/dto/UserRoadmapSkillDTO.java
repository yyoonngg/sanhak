package com.project.sanhak.mypage.dto;

import com.project.sanhak.domain.skil.code.CodeSkil;
import com.project.sanhak.domain.skil.user.UserRoadmap;
import com.project.sanhak.domain.skil.user.UserRoadmapSkil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoadmapSkillDTO {
    private int userRoadmapId; // 추가
    private String skillName; // 스킬 이름
    private int x; // x 좌표
    private int y; // y 좌표

    public UserRoadmapSkil toEntity(UserRoadmap userRoadmap, CodeSkil codeSkil) {
        UserRoadmapSkil userRoadmapSkill = new UserRoadmapSkil();
        userRoadmapSkill.setURSurid(userRoadmap); // UserRoadmap과의 연관 설정
        userRoadmapSkill.setURScsid(codeSkil);    // CodeSkil과의 연관 설정
        userRoadmapSkill.setX(this.x);
        userRoadmapSkill.setY(this.y);
        return userRoadmapSkill;
    }
}
