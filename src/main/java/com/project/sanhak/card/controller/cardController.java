package com.project.sanhak.card.controller;

import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.card.mapper.CardMapper;
import com.project.sanhak.card.repository.cardRepository;
import com.project.sanhak.card.service.cardService;
import com.project.sanhak.domain.card.ExperienceCard;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.main.service.MainService;
import com.project.sanhak.util.s3.S3FileService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> getAiCard(HttpSession session) {
        try {
            Integer uid = (Integer) session.getAttribute("uid");
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
            String result = cardService.createAiCard(card, pdfFile);
            if ("success".equals(result)) {
                return ResponseEntity.ok("{\"status\":\"success\"}");
            } else {
                return ResponseEntity.badRequest().body("카드 생성 과정에서 오류가 발생했습니다.");
            }

        } catch (Exception e) {
            log.error("서비스 실행 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
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
            ExperienceCard existingCard = cardRepository.findByECIdAndECuid(card_id, user)
                    .orElseThrow(() -> new IllegalArgumentException("카드를 찾을 수 없습니다."));
            ExperienceCard card = CardMapper.toEntity(updatedCardDTO, user);
            if (updatedCardDTO.getTitle() != null) {
                existingCard.setECTitle(updatedCardDTO.getTitle());
            }
            if (updatedCardDTO.getReflection() != null) {
                existingCard.setECReflection(updatedCardDTO.getReflection());
            }
            if(imageFile != null &&!imageFile.isEmpty()){
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
                card.setECImageUrl(imageUrl);
            }
            if(pdfFile != null &&!pdfFile.isEmpty()){
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
                card.setECPdfUrl(pdfUrl);
            } else {
                try {
                    pdfFile= S3FileService.downloadFileAsMultipartFile(updatedCardDTO.getPdfUrl());
                } catch (Exception e) {
                    log.error("PDF 파일 다운로드 실패: {}", e.getMessage());
                    return ResponseEntity.status(500).body("PDF 파일 다운로드에 실패했습니다.");
                }
            }
            // Call the service to update the card
            String result = cardService.updateAiCard(user, card_id, existingCard, imageFile, pdfFile);
            if ("success".equals(result)) {
                return ResponseEntity.ok("{\"status\":\"success\"}");
            } else {
                return ResponseEntity.badRequest().body("카드 업데이트 과정에서 오류가 발생했습니다.");
            }

        } catch (Exception e) {
            log.error("서비스 실행 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }

    @Transactional
    @PostMapping("/delete/{card_id}")
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
}