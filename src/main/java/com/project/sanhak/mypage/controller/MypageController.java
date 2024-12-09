package com.project.sanhak.mypage.controller;

import com.project.sanhak.category.repository.categoryRepository;
import com.project.sanhak.domain.skil.code.CodeSkil;
import com.project.sanhak.domain.skil.user.UserRoadmap;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "${cors.allowed.origin}")
@RequestMapping("/api/mypage")
public class MypageController {
    @Autowired
    private MypageService mypageService;
    @Autowired
    private categoryRepository categoryRepository;

    @Operation(summary = "내 로드맵 목록 호출",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "로드맵 목록 반환",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = roadmapListDTO.class)))))
    @GetMapping({"/roadmap/list/{uid}", "/roadmap/list"})
    public ResponseEntity<List<roadmapListDTO>> getMyRoadmapList(@PathVariable(required = false) Integer uid,
                                                                 HttpSession session) {
        boolean flag = false;
        if (uid == null) {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            uid = uidAttribute;
            flag=true;
        } else{
            int uidCheck = (int) session.getAttribute("uid");
            if(uid==uidCheck){
                flag=true;
            }
        }
        List<roadmapListDTO> roadmapList = mypageService.getRoadmapListByUid(uid, flag);
        return ResponseEntity.ok(roadmapList);
    }

    @Operation(summary = "내 로드맵 호출",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "로드맵 반환",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = roadmapDTO.class)))))
    @GetMapping("/roadmap/{ur_id}")
    public ResponseEntity<mergeRoadmapDTO> getMyRoadmap(HttpSession session,
                                                         @PathVariable(required = false) Integer uid,
                                                         @PathVariable int ur_id) {
        if (uid == null) {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            uid = uidAttribute;
        }
        UserRoadmap roadmapName=mypageService.getRoadmapNameByuid(uid,ur_id);
        List<roadmapDTO> roadmapList = mypageService.getRoadmaps(roadmapName);
        mergeRoadmapDTO roadmap = new mergeRoadmapDTO(ur_id,roadmapName.getURName(),roadmapName.getState(),roadmapList);
        return ResponseEntity.ok(roadmap);
    }

    @Operation(summary = "내 로드맵 호출",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "로드맵 반환",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = roadmapDTO.class)))))
    @GetMapping({"/roadmap/all", "/roadmap/all/{uid}"})
    public ResponseEntity<List<mergeRoadmapDTO>> getAllRoadmap(HttpSession session,
                                                         @PathVariable(required = false) Integer uid) {
        boolean flag = false;
        if (uid == null) {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            uid = uidAttribute;
            flag=true;
        } else{
            Integer uidCheck = (Integer) session.getAttribute("uid");
            if(uid.equals(uidCheck)){
                flag=true;
            }
        }
        List<mergeRoadmapDTO> roadmapList = new ArrayList<>();
        List<roadmapListDTO> list = mypageService.getRoadmapListByUid(uid, flag);
        for (roadmapListDTO roadmap : list) {
            UserRoadmap roadmapName=mypageService.getRoadmapNameByuid(uid, roadmap.getId());
            List<roadmapDTO> skills = mypageService.getRoadmaps(roadmapName);
            mergeRoadmapDTO mergedRoadmap = new mergeRoadmapDTO();
            mergedRoadmap.setId(roadmap.getId());
            mergedRoadmap.setName(roadmap.getName());
            mergedRoadmap.setSkills(skills);
            roadmapList.add(mergedRoadmap);
        }
        return ResponseEntity.ok(roadmapList);
    }

    @Operation(summary = "새 로드맵 추가",
            responses = @ApiResponse(responseCode = "200", description = "새 로드맵 추가 성공"))
    @GetMapping("/roadmap/add")
    public ResponseEntity<roadmapListDTO> addNewRoadmap(HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        roadmapListDTO newRoadmap=mypageService.addNewRoadmap(uid);
        return ResponseEntity.ok(newRoadmap);
    }

    @Operation(summary = "특정 로드맵 정보 업데이트",
            responses = @ApiResponse(responseCode = "200", description = "로드맵 정보 업데이트 성공"))
    @PostMapping("/roadmap/update/{ur_id}")
    public ResponseEntity<String> updateTargetRoadmap(@PathVariable int ur_id,
                                                      @RequestBody roadmapUpdateRequest requestData) {
        System.out.println("처리 중인 요청 데이터: " + requestData);
        mypageService.updateRoadmapName(ur_id, requestData.getName());
        mypageService.updateRoadmap(ur_id, requestData.getUpdates());
        return ResponseEntity.ok("로드맵 정보 업데이트 성공");
    }

    @Operation(summary = "특정 로드맵 정보 업데이트",
            responses = @ApiResponse(responseCode = "200", description = "로드맵 정보 업데이트 성공"))
    @GetMapping("/roadmap/state/{ur_id}/{state}")
    public ResponseEntity<String> updateStateRoadmap(@PathVariable int ur_id,
                                                      @PathVariable int state) {
        mypageService.updateRoadmapState(ur_id, state);
        return ResponseEntity.ok("로드맵 정보 업데이트 성공");
    }

    @Operation(summary = "마스터리 스킬 목차 보여주기",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "마스터리 스킬 리스트 반환",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = masteryDTO.class)))))
    @GetMapping("/mastery/{cs_id}")
    public ResponseEntity<mergeMasteryDTO> getMastery(@PathVariable int cs_id,
                                                       HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        mergeMasteryDTO masteryList = mypageService.getMasteryList(uid, cs_id);
        return ResponseEntity.ok(masteryList);
    }
    @GetMapping("/mastery")
    public ResponseEntity<mergeMasteryDTO> getMasteryByName(@RequestParam String name,
                                                            HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        CodeSkil skil = categoryRepository.getCodeSkilByCSName(name);
        int cs_id= skil.getCSId();
        mergeMasteryDTO masteryList = mypageService.getMasteryList(uid, cs_id);
        return ResponseEntity.ok(masteryList);
    }

    @Operation(summary = "마스터리 스킬을 익혔다 누르면 퀴즈를 생성",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "퀴즈 정보 반환",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = quizDTO.class))))
    @GetMapping("/mastery/test/{ms_id}")
    public ResponseEntity<quizDTO> testMastery(@PathVariable int ms_id) {
        quizDTO quiz = mypageService.getQuiz(ms_id);
        System.out.println(quiz);
        return ResponseEntity.ok(quiz);
    }

    @Operation(summary = "퀴즈를 맞추면 마스터리 스킬을 익히게 함",
            responses = @ApiResponse(responseCode = "200", description = "스킬 익힘 성공"))
    @GetMapping("/mastery/test/ok/{ms_id}")
    public ResponseEntity<String> testOkMastery(@PathVariable int ms_id,
                                                HttpSession session) {
        int uid;
        Integer uidAttribute = (Integer) session.getAttribute("uid");
        if (uidAttribute == null) {
            throw new NullPointerException("UID is null");
        }
        uid = uidAttribute;
        mypageService.masterSkill(uid, ms_id);
        return ResponseEntity.ok("스킬을 익혔습니다!");
    }

    @Operation(summary = "뱃지 호출",
            responses = @ApiResponse(responseCode = "200", description = "뱃지 보여주기"))
    @GetMapping({"/badge","/badge/{uid}"})
    public ResponseEntity<List<badgeDTO>> badge(HttpSession session,
                                            @PathVariable(required = false) Integer uid) {
        if (uid == null) {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            uid = uidAttribute;
        }
        List <badgeDTO> badgeList = mypageService.showBadge(uid);
        return ResponseEntity.ok(badgeList);
    }
}