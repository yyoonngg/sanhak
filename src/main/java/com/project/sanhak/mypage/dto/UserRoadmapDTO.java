package com.project.sanhak.mypage.dto;

import com.project.sanhak.domain.user.User;
import com.project.sanhak.domain.skil.user.UserRoadmap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoadmapDTO {
    private String CustomRoadMapname;

    public UserRoadmap toEntity(User user) {
        UserRoadmap userRoadmap = new UserRoadmap();
        userRoadmap.setURName(this.CustomRoadMapname);
        userRoadmap.setURuid(user); // 유저 설정
        return userRoadmap;
    }

}
