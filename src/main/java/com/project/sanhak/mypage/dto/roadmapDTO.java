package com.project.sanhak.mypage.dto;

import com.project.sanhak.category.dto.categoryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로드맵 정보 객체")
public class roadmapDTO {
    @Schema(description = "로드맵 스킬 ID", example = "55")
    private int id;
    @Schema(description = "코드 스킬 ID", example = "1")
    private int cs_id;
    @Schema(description = "스킬 이름", example = "html")
    private String name;
    @Schema(description = "부모 목록", example = "[]")
    private List<Integer> parent;
    @Schema(description = "자식 목록", example = "[56,57]")
    private List<Integer> child;
    @Schema(description = "위치", example = "[0,0]")
    private int[] position;
    @Schema(description = "태그 상태", example = "none")
    private String tag;
}