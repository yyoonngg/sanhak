package com.project.sanhak.mypage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "퀴즈 정보 객체")
public class quizDTO {
    @Schema(description = "퀴즈 내용", example = "데이터베이스에서 인덱스의 역할은 무엇인가요?")
    private String quizContents;

    @Schema(description = "정답 번호", example = "2")
    private int answer;
}
