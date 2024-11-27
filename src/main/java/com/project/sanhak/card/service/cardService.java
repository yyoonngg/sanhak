package com.project.sanhak.card.service;

import com.project.sanhak.aiChatbot.repository.chatRepository;
import com.project.sanhak.aiChatbot.repository.messageRepository;
import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.card.mapper.CardMapper;
import com.project.sanhak.card.repository.cardRepository;
import com.project.sanhak.domain.card.ExperienceCard;
import com.project.sanhak.domain.chat.ChatRooms;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.lounge.repository.LoungeRepository;
import com.project.sanhak.lounge.service.LoungeService;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class cardService {
    @Value("${api.base.url}")
    private String apiBaseUrl;

    private final CardMapper cardMapper;
    private final WebClient webClient;
    @Autowired
    private cardRepository cardRepository;
    @Autowired
    private chatRepository chatRepository;
    @Autowired
    private messageRepository messageRepository;
    @Autowired
    private LoungeService loungeService;
    @Autowired
    private LoungeRepository loungeRepository;

    public cardService(CardMapper cardMapper, WebClient webClient) {
        this.cardMapper = cardMapper;
        this.webClient = webClient;
    }

    public List<aiCardDTO> getAllMyCard(int uid) {
        List<ExperienceCard> experienceCards = cardRepository.findByECuid_UId(uid);
        return cardMapper.toDTOList(experienceCards);
    }

    public aiCardDTO getTargetCard(int ecId) {
        return cardRepository.findByECId(ecId)
                .map(CardMapper::toDTO)
                .orElse(null);
    }

    public String createAiCard(ExperienceCard card, String pdfUrl) throws IOException {

        String url = apiBaseUrl + "/createCard";

        // 요청 데이터 설정
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("title", card.getECTitle());
        List<String> toolsList = card.getECTool() != null ? Arrays.asList(card.getECSkill().split(", ")) : new ArrayList<>();
        List<String> positionList = card.getECPosition() != null ? Arrays.asList(card.getECPosition().split(", ")) : new ArrayList<>();
        requestData.put("tool", toolsList);
        requestData.put("position", positionList);
        requestData.put("reflection", card.getECReflection());
        requestData.put("pdfUrl", pdfUrl);

        System.out.println("Request data: " + requestData);

        Map<String, Object> response;
        try {
            response = webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestData)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            System.out.println("Response data: " + response); // 응답 데이터 확인

        } catch (Exception e) {
            System.out.println("API 호출 오류: " + e);
            throw new RuntimeException("외부 API 호출 실패: " + e.getMessage(), e);
        }

        // 응답에서 summary 처리
        if (response != null && response.containsKey("summary")) {
            String summary = (String) response.get("summary");
            String text = (String) response.get("pdfText");
            card.setECSummary(summary);
            card.setECText(text);
            cardRepository.save(card);
            if(loungeRepository.findByLUid(card.getECuid())!=null){
                loungeService.increaseCnum(card.getECuid());
            }

            // 채팅방 생성
            for (int type = 0; type < 3; type++) {
                ChatRooms chatRoom = new ChatRooms();
                chatRoom.setCRType(type);
                chatRoom.setCRuid(card.getECuid());
                chatRoom.setCRecid(card);
                chatRepository.save(chatRoom);
            }
            return "success";
        } else {
            System.out.println("Error: 응답에 summary가 없습니다.");
            return "error: summary not found";
        }
    }

    public String extractTextFromPDF(MultipartFile pdfFile) throws IOException {
        File tempFile = File.createTempFile("temp", ".pdf");
        pdfFile.transferTo(tempFile);
        try (PDDocument document = Loader.loadPDF(tempFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String updateAiCard(User user, int cardId, ExperienceCard card) throws IOException {
        // 외부 API 요청으로 요약 생성
        String url = apiBaseUrl + "/createCard";
        String pdfUrl= card.getECPdfUrl();
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("title", card.getECTitle());
        List<String> toolsList = card.getECTool() != null ? Arrays.asList(card.getECTool().split(", ")) : new ArrayList<>();
        List<String> positionList = card.getECPosition() != null ? Arrays.asList(card.getECPosition().split(", ")) : new ArrayList<>();
        requestData.put("tool", toolsList);
        requestData.put("position", positionList);
        requestData.put("reflection", card.getECReflection());
        requestData.put("pdfUrl", pdfUrl);

        System.out.println("Request data: " + requestData);

        Map<String, Object> response;
        try {
            response = webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestData)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            System.out.println("Response data: " + response); // 응답 데이터 확인

        } catch (Exception e) {
            System.out.println("API 호출 오류: " + e);
            throw new RuntimeException("외부 API 호출 실패: " + e.getMessage(), e);
        }

        // 응답에서 summary 처리
        if (response != null && response.containsKey("summary")) {
            String summary = (String) response.get("summary");
            String text = (String) response.get("pdfText");
            card.setECSummary(summary);
            card.setECText(text);
            cardRepository.save(card);
            return "success";
        } else {
            System.out.println("Error: 응답에 summary가 없습니다.");
            return "error: summary not found";
        }
    }

    public String deleteAiCard(ExperienceCard card) {
        List<ChatRooms> chatRooms = chatRepository.findByCRecid(card);

        for (ChatRooms chatRoom : chatRooms) {
            messageRepository.deleteByCMcrid(chatRoom);
            chatRepository.delete(chatRoom);
        }
        cardRepository.delete(card);
        return "success";
    }
}