package com.project.sanhak.mypage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "마스터리 스킬 객체")
public class masteryDTO {
    @Schema(description = "마스터리 스킬 ID", example = "1")
    private int id;

    @Schema(description = "마스터리 스킬 이름", example = "Database Optimization")
    private String name;

    @Schema(description = "정보 1", example = "SQL Tuning")
    private String info1;

    @Schema(description = "정보 2", example = "Indexing")
    private String info2;

    @Schema(description = "정보 3", example = "Query Optimization")
    private String info3;

    @Schema(description = "코드 스킬 ID", example = "10")
    private int cs_id;

    @Schema(description = "익힌 여부.", example = "true")
    private boolean state;
}