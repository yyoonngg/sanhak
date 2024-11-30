package com.project.sanhak.mypage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "마스터리 스킬 객체")
public class masteryDTO {
    @Schema(description = "마스터리 스킬 ID", example = "1")
    private int id;

    @Schema(description = "마스터리 스킬 이름", example = "Database Optimization")
    private String title;

    @Schema(description = "정보", example = "SQL Tuning")
    private List<String> subtitle;

    @Schema(description = "익힌 여부.", example = "true")
    private boolean status;
}