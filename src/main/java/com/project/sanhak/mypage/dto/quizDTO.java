package com.project.sanhak.mypage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "퀴즈 정보 객체")
public class quizDTO {
    @Schema(description = "퀴즈 문제", example = "데이터베이스에서 인덱스의 역할은 무엇인가요?")
    private String question;
    @Schema(description = "선택지", example = "[1. 별 역할 안 한다 , 2. 인덱스를 통해 보다 쉽게 컬럼 호출이 가능하다. , 3. 그냥. , 4. 모른다.]")
    private List<String> options;
    @Schema(description = "정답 번호", example = "2")
    private int answer;
}
