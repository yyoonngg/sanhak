package com.project.sanhak.mypage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "마스터리 목차")
public class mergeMasteryDTO {
    @Schema(description = "코드스킬 ID", example = "1")
    private int id;
    @Schema(description = "스킬 이름", example = "웹 개발 로드맵")
    private String name;
    private String description;
    private List<masteryDTO> list;
}
