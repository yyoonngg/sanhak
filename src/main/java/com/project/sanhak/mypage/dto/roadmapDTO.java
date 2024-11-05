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
    @Schema(description = "로드맵 ID", example = "1")
    private int id;

    @Schema(description = "로드맵 이름", example = "웹 개발 로드맵")
    private String name;

    @Schema(description = "스킬 리스트", example = "[{\"id\": 1, \"name\": \"Java\",\"List<parents>\":[],\"List<child>\":[2,3,4],\"position\":[0,0],\"tag\": \"none\" }," +
            " {\"id\": 2, \"name\": \"Spring\",\"List<parents>\":[1],\"List<child>\":[5],\"position\":[1,0],\"tag\": \"none\" }]")
    private List<categoryDTO> skilList;
}

