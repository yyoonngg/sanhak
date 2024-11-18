package com.project.sanhak.main.dto;

import com.project.sanhak.card.dto.skill;
import com.project.sanhak.domain.skil.code.Tools;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "카드 데이터 객체")
public class cardDTO {
    @Schema(description = "카드 시작 날짜", example = "2024-01-01")
    private String fromDate;

    @Schema(description = "카드 종료 날짜", example = "2024-12-31")
    private String toDate;

    @Schema(description = "카드 제목", example = "AI 개발 프로젝트")
    private String title;

    @Schema(description = "카드 카테고리 목록")
    private List<String> category;

    @Schema(description = "스킬 목록")
    private List<skill> skills;

    @Schema(description = "툴 목록")
    private List<Tools> tools;

    @Schema(description = "회고 내용", example = "이 프로젝트는 많은 학습 기회를 제공했습니다.")
    private String reflection;

    @Schema(description = "카드 요약", example = "AI 모델 학습 및 구현")
    private String summary;

    @Schema(description = "카드 ID", example = "1")
    private Integer id;
}
