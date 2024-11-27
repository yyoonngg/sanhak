package com.project.sanhak.mypage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로드맵 정보+로드맵 노드 리스트")
public class mergeRoadmapDTO {
    @Schema(description = "로드맵 ID", example = "1")
    private int id;
    @Schema(description = "로드맵 이름", example = "웹 개발 로드맵")
    private String name;
    private List<roadmapDTO> skills;
}
