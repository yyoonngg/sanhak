package com.project.sanhak.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "프로필 데이터 객체")
public class profileDTO {
    @Schema(description = "사용자 UID", example = "1")
    private int id;

    @Schema(description = "사용자 소개", example = "AI 개발자입니다.")
    private String bio;

    @Schema(description = "프로필 이미지 URL")
    private String profileImgURL;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "이메일 주소", example = "hong@example.com")
    private String email;

    @Schema(description = "희망 포지션", example="backend" )
    private String desirePosition;
    @Schema(description = "Number of badges associated with the lounge")
    private int badge_cnt;
    @Schema(description = "Number of roadmaps associated with the lounge")
    private int roadmap_cnt;
    @Schema(description = "Number of cards associated with the lounge")
    private int card_cnt;

}