package com.project.sanhak.card.controller;

import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.card.mapper.CardMapper;
import com.project.sanhak.card.repository.cardRepository;
import com.project.sanhak.card.service.cardService;
import com.project.sanhak.domain.card.ExperienceCard;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.main.service.MainService;
import com.project.sanhak.util.s3.S3FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "${cors.allowed.origin}")
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

    @Operation(summary = "내 경험 카드 전체 호출")
    @ApiResponse(responseCode = "200", description = "전체 경험 카드 목록",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = aiCardDTO.class))))
    @GetMapping(value = {"/", "/{uid}"})
    public ResponseEntity<?> getAiCard(HttpSession session, @PathVariable(required = false) Integer uid) {
        try {
            if (uid == null) {
                Integer uidAttribute = (Integer) session.getAttribute("uid");
                if (uidAttribute == null) {
                    throw new NullPointerException("UID is null");
                }
                uid = uidAttribute;
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("오류가 발생했습니다: " + e.getMessage());
        }
        List<aiCardDTO> cardList = cardService.getAllMyCard(uid);
        return ResponseEntity.ok(cardList);
    }


    @Operation(summary = "내 경험 카드 생성")
    @ApiResponse(responseCode = "200", description = "경험 카드 생성 성공 메시지")
    @PostMapping("/create")
    @Transactional
    public ResponseEntity<String> createAiCard(HttpSession session,
                                               @RequestPart("cardInfo") aiCardDTO cardInfoDTO,
                                               @RequestPart("image") MultipartFile imageFile,
                                               @RequestPart("pdfFile") MultipartFile pdfFile) {
        try {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;
            User user = userService.getUserFromUid(uid);
            cardInfoDTO.setPdfName(pdfFile.getOriginalFilename());
            ExperienceCard card = CardMapper.toEntity(cardInfoDTO, user);
            String imageUrl = null;
            if (!imageFile.isEmpty()) {
                try {
                    imageUrl = S3FileService.upload(imageFile);
                } catch (Exception e) {
                    log.warn("이미지 업로드 실패: {}", e.getMessage());
                    return ResponseEntity.badRequest().body("이미지 파일 업로드에 실패했습니다.");
                }
            }
            String pdfUrl;
            try {
                pdfUrl = S3FileService.upload(pdfFile);
            } catch (Exception e) {
                log.error("PDF 파일 업로드 실패: {}", e.getMessage());
                return ResponseEntity.badRequest().body("PDF 파일 업로드에 실패했습니다.");
            }
            card.setECImageUrl(imageUrl);
            card.setECPdfUrl(pdfUrl);
            String result = cardService.createAiCard(card, pdfUrl);
            if ("success".equals(result)) {
                return ResponseEntity.ok("{\"status\":\"success\"}");
            } else {
                return ResponseEntity.status(500).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
            }

        } catch (Exception e) {
            log.error("서비스 실행 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }

    @Operation(summary = "특정 경험카드 읽어오기")
    @ApiResponse(responseCode = "200", description = "경험 카드 정보",
            content = @Content(schema = @Schema(implementation = aiCardDTO.class)))
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

    @Operation(summary = "내 경험 카드 수정")
    @ApiResponse(responseCode = "200", description = "경험 카드 수정 성공 메시지")
    @PostMapping("/update/{card_id}")
    @Transactional
    public ResponseEntity<String> updateAiCard(@PathVariable int card_id,
                                               HttpSession session,
                                               @RequestPart("cardInfo") aiCardDTO updatedCardDTO,
                                               @RequestPart(value = "image", required = false) MultipartFile imageFile,
                                               @RequestPart(value = "pdfFile", required = false) MultipartFile pdfFile) {
        try {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;
            User user = userService.getUserFromUid(uid);
            ExperienceCard existingCard = cardRepository.findById(card_id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다."));
            ExperienceCard updatedCard = CardMapper.toEntity(updatedCardDTO, user);
            updateFields(existingCard, updatedCard);

            if (updatedCardDTO.getTitle() != null) {
                existingCard.setECTitle(updatedCardDTO.getTitle());
            }
            if (updatedCardDTO.getReflection() != null) {
                existingCard.setECReflection(updatedCardDTO.getReflection());
            }
            if (imageFile != null && !imageFile.isEmpty()) {
                S3FileService.deleteFileFromS3(updatedCardDTO.getImageUrl());
                String imageUrl = null;
                if (!imageFile.isEmpty()) {
                    try {
                        imageUrl = S3FileService.upload(imageFile);
                        existingCard.setECImageUrl(imageUrl);
                    } catch (Exception e) {
                        log.warn("이미지 업로드 실패: {}", e.getMessage());
                    }
                }
                existingCard.setECImageUrl(imageUrl);
            }
            if (pdfFile != null && !pdfFile.isEmpty()) {
                S3FileService.deleteFileFromS3(updatedCardDTO.getPdfUrl());
                String pdfUrl;
                try {
                    pdfUrl = S3FileService.upload(pdfFile);
                    existingCard.setECPdfUrl(pdfUrl);
                    existingCard.setECPdfName(pdfFile.getOriginalFilename());
                } catch (Exception e) {
                    log.error("PDF 파일 업로드 실패: {}", e.getMessage());
                    return ResponseEntity.badRequest().body("PDF 파일 업로드에 실패했습니다.");
                }
                existingCard.setECPdfUrl(pdfUrl);
            }
            // Call the service to update the card
            String result = cardService.updateAiCard(user, card_id, existingCard);
            if ("success".equals(result)) {
                return ResponseEntity.ok("{\"status\":\"success\"}");
            } else {
                return ResponseEntity.status(500).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
            }

        } catch (Exception e) {
            log.error("서비스 실행 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }

    @Operation(summary = "내 경험 카드 삭제")
    @ApiResponse(responseCode = "200", description = "경험 카드 삭제 성공 메시지")
    @PostMapping("/delete/{card_id}")
    @Transactional
    public ResponseEntity<?> deleteAiCard(@PathVariable int card_id, HttpSession session) {
        try {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
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

    private void updateFields(ExperienceCard existingCard, ExperienceCard updatedCard) {
        if (updatedCard.getECFromDate() != null) {
            existingCard.setECFromDate(updatedCard.getECFromDate());
        }
        if (updatedCard.getECToDate() != null) {
            existingCard.setECToDate(updatedCard.getECToDate());
        }
        if (updatedCard.getECTitle() != null) {
            existingCard.setECTitle(updatedCard.getECTitle());
        }
        if (updatedCard.getECPosition() != null) {
            existingCard.setECPosition(updatedCard.getECPosition());
        }
        if (updatedCard.getECSkill() != null) {
            existingCard.setECSkill(updatedCard.getECSkill());
        }
        if (updatedCard.getECTool() != null) {
            existingCard.setECTool(updatedCard.getECTool());
        }
        if (updatedCard.getECReflection() != null) {
            existingCard.setECReflection(updatedCard.getECReflection());
        }
        if (updatedCard.getECSummary() != null) {
            existingCard.setECSummary(updatedCard.getECSummary());
        }
        if (updatedCard.getECImageUrl() != null) {
            existingCard.setECImageUrl(updatedCard.getECImageUrl());
        }
        if (updatedCard.getECPdfName() != null) {
            existingCard.setECPdfName(updatedCard.getECPdfName());
        }
        if (updatedCard.getECPdfUrl() != null) {
            existingCard.setECPdfUrl(updatedCard.getECPdfUrl());
        }
        if (updatedCard.getECLink() != null) {
            existingCard.setECLink(updatedCard.getECLink());
        }
        if (updatedCard.getECText() != null) {
            existingCard.setECText(updatedCard.getECText());
        }
    }
}