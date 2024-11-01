package com.project.sanhak.card.controller;

import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.card.mapper.CardMapper;
import com.project.sanhak.card.repository.cardRepository;
import com.project.sanhak.card.service.cardService;
import com.project.sanhak.domain.card.ExperienceCard;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.main.service.MainService;
import com.project.sanhak.util.s3.S3FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

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
    @Autowired
    private cardRepository cardRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAiCard(Authentication authentication) {
        try {
            if (authentication == null || !(authentication.getPrincipal() instanceof OAuth2User)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 정보가 없습니다.");
            }
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Integer uid = oAuth2User.getAttribute("uid");
            if (uid == null) {
                throw new NullPointerException("UID is null");
            }
            List<aiCardDTO> cardList = cardService.getAllMyCard(uid);
            return ResponseEntity.ok(cardList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Transactional
    @PostMapping("/create")
    public Mono<ResponseEntity<String>> createAiCard(Authentication authentication,
                                                     @RequestPart("cardInfo") aiCardDTO cardInfoDTO,
                                                     @RequestPart("image") MultipartFile imageFile,
                                                     @RequestPart("pdfFile") MultipartFile pdfFile) {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Integer uidAttribute = oAuth2User.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;
            User user = userService.getUserFromUid(uid);
            ExperienceCard card = CardMapper.toEntity(cardInfoDTO, user);
            String imageUrl = null;
            if (!imageFile.isEmpty()) {
                try {
                    imageUrl = S3FileService.upload(imageFile);
                } catch (Exception e) {
                    log.warn("이미지 업로드 실패: {}", e.getMessage());
                }
            }
            String pdfUrl;
            try {
                pdfUrl = S3FileService.upload(pdfFile);
            } catch (Exception e) {
                log.error("PDF 파일 업로드 실패: {}", e.getMessage());
                return Mono.just(ResponseEntity.badRequest().body("PDF 파일 업로드에 실패했습니다."));
            }
            card.setECImageUrl(imageUrl);
            card.setECPdfUrl(pdfUrl);
            return cardService.createAiCard(card, pdfFile)
                    .map(result -> {
                        if ("success".equals(result)) {
                            return ResponseEntity.status(302)
                                    .header("Location", "/cards/list")
                                    .body("");
                        } else {
                            return ResponseEntity.badRequest().body("카드 생성 과정에서 오류가 발생했습니다.");
                        }
                    })
                    .onErrorResume(error -> {
                        log.error("서비스 실행 중 오류 발생: {}", error.getMessage());
                        return Mono.just(ResponseEntity.internalServerError()
                                .body("서버 오류가 발생했습니다. 다시 시도해주세요."));
                    });
        } catch (Exception e) {
            return Mono.just(ResponseEntity.badRequest().body("사용자 인증 실패: " + e.getMessage()));
        }
    }

    @GetMapping("/read/{card_id}")
    public ResponseEntity<?> readAiCard(@PathVariable int card_id) {
        try {
            aiCardDTO card = cardService.getTargetCard(card_id);
            return ResponseEntity.ok(card);
        } catch (Exception e) {
            log.error("카드 읽기 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body("카드를 읽는 도중 오류가 발생했습니다.");
        }
    }

    @Transactional
    @PostMapping("/update/{card_id}")
    public Mono<ResponseEntity<String>> updateAiCard(@PathVariable int card_id,
                                                     Authentication authentication,
                                                     @RequestPart("cardInfo") aiCardDTO updatedCardDTO,
                                                     @RequestPart("image") MultipartFile imageFile,
                                                     @RequestPart("pdfFile") MultipartFile pdfFile) {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Integer uidAttribute = oAuth2User.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;
            User user = userService.getUserFromUid(uid);
            cardRepository.findByECIdAndECuid(card_id, user)
                    .orElseThrow(() -> new IllegalArgumentException("카드를 찾을 수 없습니다."));
            ExperienceCard card = CardMapper.toEntity(updatedCardDTO, user);
            if(!imageFile.isEmpty()){
                S3FileService.deleteFileFromS3(updatedCardDTO.getImageUrl());
                String imageUrl = null;
                if (!imageFile.isEmpty()) {
                    try {
                        imageUrl = S3FileService.upload(imageFile);
                    } catch (Exception e) {
                        log.warn("이미지 업로드 실패: {}", e.getMessage());
                    }
                }
                card.setECImageUrl(imageUrl);
            }
            if(!pdfFile.isEmpty()){
                S3FileService.deleteFileFromS3(updatedCardDTO.getPdfUrl());
                String pdfUrl;
                try {
                    pdfUrl = S3FileService.upload(pdfFile);
                } catch (Exception e) {
                    log.error("PDF 파일 업로드 실패: {}", e.getMessage());
                    return Mono.just(ResponseEntity.badRequest().body("PDF 파일 업로드에 실패했습니다."));
                }
                card.setECPdfUrl(pdfUrl);
            } else {
                try {
                    MultipartFile existingPdfFile = S3FileService.downloadFileAsMultipartFile(updatedCardDTO.getPdfUrl());
                } catch (Exception e) {
                    log.error("PDF 파일 다운로드 실패: {}", e.getMessage());
                    return Mono.just(ResponseEntity.internalServerError().body("PDF 파일 다운로드에 실패했습니다."));
                }
            }
            return cardService.updateAiCard(user, card_id, card, imageFile, pdfFile)
                    .map(result -> {
                        if ("success".equals(result)) {
                            return ResponseEntity.ok("카드가 성공적으로 업데이트되었습니다.");
                        } else {
                            return ResponseEntity.badRequest().body("카드 업데이트 과정에서 오류가 발생했습니다.");
                        }
                    })
                    .onErrorResume(error -> {
                        log.error("서비스 실행 중 오류 발생: {}", error.getMessage());
                        return Mono.just(ResponseEntity.internalServerError()
                                .body("서버 오류가 발생했습니다. 다시 시도해주세요."));
                    });
        } catch (Exception e) {
            return Mono.just(ResponseEntity.badRequest().body("사용자 인증 실패: " + e.getMessage()));
        }
    }

    @Transactional
    @PostMapping("/delete/{card_id}")
    public ResponseEntity<?> deleteAiCard(@PathVariable int card_id, Authentication authentication) {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Integer uidAttribute = oAuth2User.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;
            User user = userService.getUserFromUid(uid);
            ExperienceCard card = cardRepository.findByECIdAndECuid(card_id, user)
                    .orElseThrow(() -> new IllegalArgumentException("카드를 찾을 수 없습니다."));
            String result = cardService.deleteAiCard(card);
            if ("success".equals(result)) {
                S3FileService.deleteFileFromS3(card.getECPdfUrl());
                S3FileService.deleteFileFromS3(card.getECImageUrl());
                return ResponseEntity.ok("카드가 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.badRequest().body("카드 삭제 도중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            log.error("카드 삭제 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body("카드를 삭제하는 과정에서 오류가 발생했습니다.");
        }
    }
}