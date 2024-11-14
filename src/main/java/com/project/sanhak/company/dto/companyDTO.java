package com.project.sanhak.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description ="comapnyDTO")
public class companyDTO {
    @Schema(description = "The unique ID of the lounge")
    private int id;
    @Schema(description = "이름")
    private String name;
    @Schema(description = "위치")
    private String location;
    @Schema(description = "포지션")
    private String position;
    @Schema(description = "부가 설명")
    private String description;
    @Schema(description = "스킬 종류")
    private String skill;
}
