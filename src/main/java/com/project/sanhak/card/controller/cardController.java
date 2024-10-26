package com.project.sanhak.card.controller;

import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.card.mapper.CardMapper;
import com.project.sanhak.main.service.MainService;
import com.project.sanhak.card.service.cardService;
import com.project.sanhak.domain.card.ExperienceCard;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.util.s3.S3FileService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.sanhak.util.authUtil;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/card")
public class cardController {
    @Autowired
    private MainService userService;
    @Autowired
    private S3FileService S3FileService;
    @Autowired
    private cardService cardService;

    @GetMapping("/")
    public List<aiCardDTO> getAiCard(@RequestHeader("Authorization") String token) throws JSONException {
        int uid = authUtil.getToken(token);
        return cardService.getAllMyCard(uid);
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<String>> createAiCard(@RequestHeader("Authorization") String token,
                                                     @RequestPart("cardInfo") aiCardDTO cardInfoDTO,
                                                     @RequestPart("image") MultipartFile imageFile,
                                                     @RequestPart("pdfFile") MultipartFile pdfFile) throws JSONException {
        // User 정보 가져오기 (토큰으로 인증된 사용자 조회)
        User user = userService.getUserFromToken(token);

        // DTO를 엔티티로 변환
        ExperienceCard card = CardMapper.toEntity(cardInfoDTO, user);

        // 이미지 파일 업로드 처리
        String imageUrl = null;
        if (!imageFile.isEmpty()) {
            try {
                imageUrl = S3FileService.upload(imageFile); // 이미지 업로드 시도
            } catch (Exception e) {
                log.warn("이미지 업로드 실패: {}", e.getMessage()); // 이미지 업로드 실패 시 경고 로그 기록
            }
        }

        // PDF 파일 업로드 처리 (PDF 파일은 필수)
        String pdfUrl;
        try {
            pdfUrl = S3FileService.upload(pdfFile); // PDF 파일 업로드
        } catch (Exception e) {
            log.error("PDF 파일 업로드 실패: {}", e.getMessage()); // 오류 로그 기록
            return Mono.just(ResponseEntity.badRequest().body("PDF 파일 업로드에 실패했습니다.")); // PDF 업로드 실패 시 400 응답 반환
        }

        // 업로드된 URL을 엔티티에 저장
        card.setECImageUrl(imageUrl); // 이미지 URL 저장 (null일 수 있음)
        card.setECPdfUrl(pdfUrl);     // PDF URL 저장

        // 데이터 저장 및 서비스 실행
        return cardService.createAiCard(card, pdfFile)
                .map(result -> {
                    if ("success".equals(result)) {
                        // 성공 시 목록 페이지로 리다이렉트
                        return ResponseEntity.status(302)
                                .header("Location", "/cards/list")
                                .body(""); // 빈 문자열을 body로 설정하여 ResponseEntity<String> 반환
                    } else {
                        // 실패 시 오류 메시지 반환
                        return ResponseEntity.badRequest().body("카드 생성 과정에서 오류가 발생했습니다.");
                    }
                })
                .onErrorResume(error -> {
                    // 서비스 실행 중 예외 발생 시 처리
                    log.error("서비스 실행 중 오류 발생: {}", error.getMessage());
                    return Mono.just(ResponseEntity.internalServerError()
                            .body("서버 오류가 발생했습니다. 다시 시도해주세요."));
                });
    }

    @PostMapping("/read/{card_id}")
    public aiCardDTO readAiCard(@PathVariable(required = false) int card_id,
                                      @RequestHeader("Authorization") String token) throws JSONException {
        return cardService.getTargetCard(card_id);
    }

    @PostMapping("/update/{card_id}")
    public String updateAiCard(@PathVariable(required = false) String card_id,
                               @RequestHeader("Authorization") String token) throws JSONException {
        int uid = authUtil.getToken(token);
        return cardService.updateAiCard(uid, card_id);
    }

    @PostMapping("/delete/{card_id}")
    public String deleteAiCard(@PathVariable(required = false) String card_id,
                               @RequestHeader("Authorization") String token) throws JSONException {
        int uid = authUtil.getToken(token);
        return cardService.deleteAiCard(uid, card_id);
    }

}