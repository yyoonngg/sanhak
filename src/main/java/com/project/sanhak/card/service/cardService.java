package com.project.sanhak.card.service;

import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.card.repository.cardRepository;
import com.project.sanhak.domain.card.ExperienceCard;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import com.project.sanhak.card.mapper.CardMapper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class cardService {
    @Autowired
    private cardRepository cardRepository;

    private final CardMapper cardMapper;
    private final WebClient webClient;

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

    private String extractTextFromPDF(MultipartFile pdfFile) {
        try (PDDocument document = Loader.loadPDF((RandomAccessRead) pdfFile.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String updateAiCard(int uid, String cardId) {
        return null;
    }

    public String deleteAiCard(int uid, String cardId) {

        return null;
    }

}