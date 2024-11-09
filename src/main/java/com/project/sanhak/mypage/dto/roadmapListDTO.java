package com.project.sanhak.mypage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로드맵 리스트 객체")
public class roadmapListDTO {
    @Schema(description = "로드맵 ID", example = "1")
    private int id;

    @Schema(description = "로드맵 이름", example = "웹 개발 로드맵")
    private String name;

    @Schema(description = "로드맵 상태", example = "0")
    private int state;
}
