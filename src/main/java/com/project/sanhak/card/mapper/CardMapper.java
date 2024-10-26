package com.project.sanhak.card.mapper;

import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.domain.card.ExperienceCard;
import com.project.sanhak.domain.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardMapper {

    // DTO->domain
    public static ExperienceCard toEntity(aiCardDTO dto, User user) {
        ExperienceCard card = new ExperienceCard();
        card.setECFromDate(LocalDateTime.parse(dto.getFromDate()));
        card.setECToDate(LocalDateTime.parse(dto.getToDate()));
        card.setECTitle(dto.getTitle());
        card.setECPosition(null);  // Position 값이 없으므로 null 처리 (또는 다른 값 설정)
        card.setECSkill(String.join(", ", dto.getSkills()));  // 리스트를 문자열로 변환
        card.setECTool(String.join(", ", dto.getTools()));    // 리스트를 문자열로 변환
        card.setECReflection(dto.getReflection());
        card.setECSummary(dto.getSummary());
        card.setECImageUrl(dto.getImageUrl());
        card.setECPdfName(dto.getPdfName());
        card.setECPdfUrl(dto.getPdfUrl());
        card.setECLink(String.join(", ", dto.getSourceUrl()));  // 리스트를 문자열로 변환
        card.setECuid(user);  // 연결된 사용자 설정

        return card;
    }

    // domain->dto
    public static aiCardDTO toDTO(ExperienceCard card) {
        return new aiCardDTO(
                card.getECFromDate().toString(),
                card.getECToDate().toString(),
                card.getECTitle(),
                Arrays.asList(card.getECPosition().split(", ")),
                Arrays.asList(card.getECSkill().split(", ")),
                Arrays.asList(card.getECTool().split(", ")),
                card.getECReflection(),
                card.getECImageUrl(),
                card.getECPdfName(),
                card.getECPdfUrl(),
                Arrays.asList(card.getECLink().split(", ")),
                card.getECSummary()
        );
    }

    public List<aiCardDTO> toDTOList(List<ExperienceCard> experienceCards) {
        return experienceCards.stream()
                .map(CardMapper::toDTO)
                .collect(Collectors.toList());
    }
}
