package com.project.sanhak.mypage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로드맵 변경 정보 객체")
public class changeRoadmapDTO {
    @Schema(description = "코드 스킬 ID. 만약 이게 라인이면 0", example = "1")
    private int id;

    @Schema(description = "상태 -> add는 1, delete는 0", example = "1")
    private int state;

    @Schema(description = "매핑 -> 노드면 이게 x,y 좌표, 라인이면 부모,자식", example = "[1, 2]")
    private List<Integer> mapping;
}
