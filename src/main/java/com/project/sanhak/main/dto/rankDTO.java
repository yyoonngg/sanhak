package com.project.sanhak.main.dto;

import com.project.sanhak.domain.user.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "랭킹 데이터 객체")
public class rankDTO {
    @Schema(description = "획득한 배지 개수", example = "5")
    private int badge_cnt;

    @Schema(description = "사용자 정보 객체")
    private UserInfo userInfo;
}