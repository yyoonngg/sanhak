package com.project.sanhak.mypage.controller;

import com.project.sanhak.mypage.dto.*;
import com.project.sanhak.mypage.service.MypageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mypage")
public class MypageController {
    @Autowired
    private MypageService mypageService;

    @Operation(summary = "내 로드맵 목록 호출",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "로드맵 목록 반환",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = roadmapListDTO.class)))))
    @GetMapping("/roadmap/list")
    public ResponseEntity<List<roadmapListDTO>> getMyRoadmapList(HttpSession session) {
        // 세션에서 uid 가져오기
        int uid = (int) session.getAttribute("uid");
        List<roadmapListDTO> roadmapList = mypageService.getRoadmapListByUid(uid);
        return ResponseEntity.ok(roadmapList);
    }

    @Operation(summary = "내 로드맵 호출",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "로드맵 반환",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = roadmapDTO.class)))))
    @GetMapping("/roadmap/{ur_id}")
    public ResponseEntity<List<roadmapDTO>> getMyRoadmap(HttpSession session,
                                                         @PathVariable int ur_id) {
        // 세션에서 uid 가져오기
        int uid = (int) session.getAttribute("uid");
        List<roadmapDTO> roadmapList = mypageService.getRoadmapsByUid(uid,ur_id);
        return ResponseEntity.ok(roadmapList);
    }

        @Operation(summary = "새 로드맵 추가",
            responses = @ApiResponse(responseCode = "200", description = "새 로드맵 추가 성공"))
    @GetMapping("/roadmap/add")
    public ResponseEntity<String> addNewRoadmap(HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        mypageService.addNewRoadmap(uid);
        return ResponseEntity.ok("새 로드맵 추가 성공");
    }

    @Operation(summary = "특정 로드맵 정보 업데이트",
            responses = @ApiResponse(responseCode = "200", description = "로드맵 정보 업데이트 성공"))
    @PostMapping("/roadmap/update/{ur_id}")
    public ResponseEntity<String> updateTargetRoadmap(@PathVariable int ur_id,
                                                      @RequestBody List<changeRoadmapDTO> requestData,
                                                      HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        mypageService.updateRoadmap(ur_id, requestData);
        return ResponseEntity.ok("로드맵 정보 업데이트 성공");
    }

    @Operation(summary = "마스터리 스킬 목차 보여주기",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "마스터리 스킬 리스트 반환",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = masteryDTO.class)))))
    @GetMapping("/mastery/{cs_id}")
    public ResponseEntity<List<masteryDTO>> getMastery(@PathVariable int cs_id,
                                                       HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        List<masteryDTO> masteryList = mypageService.getMasteryList(uid, cs_id);
        return ResponseEntity.ok(masteryList);
    }

    @Operation(summary = "마스터리 스킬을 익혔다 누르면 퀴즈를 생성",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "퀴즈 정보 반환",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = quizDTO.class))))
    @GetMapping("/mastery/test/{ms_id}")
    public ResponseEntity<quizDTO> testMastery(@PathVariable int ms_id,
                                               HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        quizDTO quiz = mypageService.getQuiz(ms_id);
        return ResponseEntity.ok(quiz);
    }

    @Operation(summary = "퀴즈를 맞추면 마스터리 스킬을 익히게 함",
            responses = @ApiResponse(responseCode = "200", description = "스킬 익힘 성공"))
    @GetMapping("/mastery/test/ok/{ms_id}")
    public ResponseEntity<String> testOkMastery(@PathVariable int ms_id,
                                                HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        mypageService.masterSkill(uid, ms_id);
        return ResponseEntity.ok("스킬을 익혔습니다!");
    }
}