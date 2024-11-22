package com.project.sanhak.main.controller;

import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.domain.user.OAuthToken;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.main.dto.cardDTO;
import com.project.sanhak.main.dto.profileDTO;
import com.project.sanhak.main.dto.rankDTO;
import com.project.sanhak.main.repository.OAuthTokenRepository;
import com.project.sanhak.main.repository.UserRepository;
import com.project.sanhak.main.service.MainService;
import com.project.sanhak.main.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "${cors.allowed.origin}")
@RequestMapping("/api/main")
public class MainController {
    @Autowired
    private MainService mainService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OAuthTokenRepository oAuthTokenRepository;

    @Operation(summary = "랭킹 보여주는 파트. 정렬은 미구현.",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "랭킹 목록 반환",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = rankDTO.class)))))
    @GetMapping(value = {"/rank/{sort}", "/rank"})
    public ResponseEntity<List<rankDTO>> getRank(@PathVariable(required = false) String sort) {
        List<rankDTO> rankList = mainService.getRankList(sort);
        return ResponseEntity.ok(rankList);
    }

    @Operation(summary = "경험카드를 보여주는 파트. 정렬은 미구현.",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "카드 목록 반환",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = cardDTO.class)))))
    @GetMapping(value = {"/card/{uid}", "/card"})
    public ResponseEntity<List<aiCardDTO>> getCard(@PathVariable(required = false) Integer uid,
                                                   HttpSession session) {
        if (uid == null) {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            uid = uidAttribute;
        }
        List<aiCardDTO> cardList = mainService.getCardList(uid);
        return ResponseEntity.ok(cardList);
    }

    @Operation(summary = "내꺼 및 타인의 프로필 보여주는 파트.",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "프로필 정보 반환",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = profileDTO.class))))
    @GetMapping(value = {"/profile/{uid}", "/profile"})
    public ResponseEntity<profileDTO> getProfile(@PathVariable(required = false) Integer uid,
                                                 HttpSession session) {
        if (uid == null) {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            uid = uidAttribute;
        }
        profileDTO profile = profileService.getProfile(uid);
        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "내 프로필 수정 파트.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로필 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "프로필 수정 실패")
            })
    @PostMapping("/profile/update")
    public ResponseEntity<String> updateProfile(@RequestPart profileDTO profile,
                                                @RequestPart("image") MultipartFile imageFile,
                                                HttpSession session) throws IOException {
        Integer uidAttribute = (Integer) session.getAttribute("uid");
        if (uidAttribute == null) {
            throw new NullPointerException("UID is null");
        }
        int uid = uidAttribute;
        profileService.updateProfile(uid, profile, imageFile);
        return ResponseEntity.ok("프로필 수정 성공");
    }

    @GetMapping("/test")
    public ResponseEntity<List<String>> testLogic(HttpSession session){
        Integer uidAttribute = (Integer) session.getAttribute("uid");
        if (uidAttribute == null) {
            return ResponseEntity.ok(Collections.singletonList("null"));
        }
        int uid = uidAttribute;
        User user= userRepository.findByUId(uid);
        String email=user.getUEmailId();
        OAuthToken token = oAuthTokenRepository.findByEmail(email);
        List<String> response= new ArrayList<>();
        response.add(token.getUsername());
        response.add(email);
        return ResponseEntity.ok(response);
    }
}
