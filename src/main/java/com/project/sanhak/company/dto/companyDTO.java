package com.project.sanhak.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description ="comapnyDTO")
public class companyDTO {
    @Schema(description = "The unique ID of the lounge")
    private int id;
    @Schema(description = "이름")
    private String title;
    @Schema(description = "포지션")
    private String category;
    @Schema(description = "부가 설명")
    private String name;
    private Double congruence;
    private String imgUrl;
    private String openingUrl;
}