package com.project.sanhak.card.service;

import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.card.repository.cardRepository;
import com.project.sanhak.domain.card.ExperienceCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class cardService {
    @Autowired
    private cardRepository cardRepository;

    public List<aiCardDTO> getAllMyCard(int uid) {
        return cardRepository.findByUserUid(uid)
                .stream()
                .map(this::convertToAiCardDTO)
                .collect(Collectors.toList());
    }

    private aiCardDTO convertToAiCardDTO(ExperienceCard card) {
        return new aiCardDTO(
                String.valueOf(card.getECFromDate()),
                String.valueOf(card.getECToDate()),
                card.getECTitle(),
                parseComma(card.getECSkil()),
                parseComma(card.getECTool()),
                card.getECReflection(),
                card.getECImageUrl(),
                card.getECPdfUrl(),
                card.getECLink()
        );
    }
    private List<String> parseComma(String value) {
        return Arrays.asList(value.split(","))
                .stream()
                .map(String::trim)  // 공백 제거
                .collect(Collectors.toList());
    }
}