package com.project.sanhak.card.service;

import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.card.mapper.CardMapper;
import com.project.sanhak.card.repository.cardRepository;
import com.project.sanhak.aiChatbot.repository.chatRepository;
import com.project.sanhak.aiChatbot.repository.messageRepository;
import com.project.sanhak.domain.card.ExperienceCard;
import com.project.sanhak.domain.chat.ChatRooms;
import com.project.sanhak.domain.user.User;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class cardService {
    private final CardMapper cardMapper;
    private final WebClient webClient;
    @Autowired
    private cardRepository cardRepository;
    @Autowired
    private chatRepository chatRepository;
    @Autowired
    private messageRepository messageRepository;
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

    public Mono<String> createAiCard(ExperienceCard card, MultipartFile pdfFile) {
        String pdfText = extractTextFromPDF(pdfFile);
        String url = "http://api/createCard";
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("title", card.getECTitle());
        requestData.put("position", card.getECPosition());
        requestData.put("tool", card.getECTool());
        requestData.put("reflection", card.getECReflection());
        requestData.put("pdfText", pdfText);

        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(requestData)
                .retrieve()
                .bodyToMono(Map.class)
                .publishOn(Schedulers.boundedElastic())
                .map(response -> {
                    if (response.containsKey("summary")) {
                        String summary = (String) response.get("summary");
                        card.setECSummary(summary);
                        cardRepository.save(card);
                        for (int type = 0; type < 3; type++) {
                            ChatRooms chatRoom = new ChatRooms();
                            chatRoom.setCRType(type);
                            chatRoom.setCRuid(card.getECuid());
                            chatRoom.setCRecid(card);
                            chatRoom.setCRLastmessage("채팅방이 개설되었습니다.");
                            chatRepository.save(chatRoom);
                        }
                        return "success";
                    } else {
                        return "error: summary not found";
                    }
                });
    }

    private String extractTextFromPDF(MultipartFile pdfFile) {
        try (PDDocument document = Loader.loadPDF((RandomAccessRead) pdfFile.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public Mono<String> updateAiCard(User user, int cardId, ExperienceCard card, MultipartFile imageFile, MultipartFile pdfFile) {
        String pdfText = extractTextFromPDF(pdfFile);
        // 외부 API 요청으로 요약 생성
        String url = "http://api/createCard";
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("title", card.getECTitle());
        requestData.put("position", card.getECPosition());
        requestData.put("tool", card.getECTool());
        requestData.put("reflection", card.getECReflection());
        requestData.put("pdfText", pdfText);

        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(requestData)
                .retrieve()
                .bodyToMono(Map.class)
                .publishOn(Schedulers.boundedElastic())
                .map(response -> {
                    if (response.containsKey("summary")) {
                        String summary = (String) response.get("summary");
                        card.setECSummary(summary);
                        cardRepository.save(card);
                        return "success";
                    } else {
                        return "error: summary not found";
                    }
                });
    }

    public String deleteAiCard(ExperienceCard card) {
        List<ChatRooms> chatRooms = chatRepository.findByCRecid(card);

        for (ChatRooms chatRoom : chatRooms) {
            messageRepository.deleteByChatRoom(chatRoom);
            chatRepository.delete(chatRoom);
        }
        cardRepository.delete(card);
        return "success";
    }
}