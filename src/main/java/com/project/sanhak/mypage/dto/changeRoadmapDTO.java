package com.project.sanhak.mypage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로드맵 변경 요청 데이터")
public class changeRoadmapDTO {
    @Schema(description = "작업 타입 (노드 추가/삭제 또는 라인 추가/삭제)", example = "add_node, delete_line")
    private String actionType;

    @Schema(description = "노드 ID 또는 라인 ID (라인 작업 시 필요)", example = "1")
    private Integer id;

    private Integer csId;

    @Schema(description = "노드 이름 (노드 추가 시 필요)", example = "Skill A")
    private String name;

    @Schema(description = "노드 추가 시 위치 (x, y 좌표)", example = "[55, 56]")
    private List<Integer> position;

    @Schema(description = "라인 추가 시 부모, 자식 매핑", example = "[parentId, childId]")
    private List<String> mapping;
}

