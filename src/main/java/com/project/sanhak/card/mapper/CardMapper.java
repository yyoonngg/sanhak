package com.project.sanhak.card.mapper;

import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.card.dto.skill;
import com.project.sanhak.category.repository.categoryRepository;
import com.project.sanhak.category.repository.toolsRepository;
import com.project.sanhak.domain.card.ExperienceCard;
import com.project.sanhak.domain.skil.code.Tools;
import com.project.sanhak.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CardMapper {
    private static toolsRepository toolsRepository;
    private static categoryRepository categoryRepository;

    @Autowired
    public CardMapper(toolsRepository toolsRepository, categoryRepository categoryRepository) {
        CardMapper.toolsRepository = toolsRepository;
        CardMapper.categoryRepository = categoryRepository;
    }
    // DTO->domain
    public static ExperienceCard toEntity(aiCardDTO dto, User user) {
        ExperienceCard card = new ExperienceCard();
        card.setECFromDate(LocalDate.parse(dto.getFromDate()));
        card.setECToDate(LocalDate.parse(dto.getToDate()));
        card.setECTitle(dto.getTitle());
        card.setECPosition(String.join(", ", dto.getCategory()));
        card.setECSkill(
                dto.getSkills().stream()
                        .map(skill::getName)
                        .collect(Collectors.joining(", "))
        );
        card.setECTool(dto.getTools().stream()
                .map(Tools::getName)
                .collect(Collectors.joining(", "))
        );    // 리스트를 문자열로 변환
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
        List<String> skillNames = Arrays.asList(card.getECSkill().split(", "));
        List<String> ToolsNames = Arrays.asList(card.getECTool().split(","));
        List<skill> skills = categoryRepository.findByCSNameIn(skillNames).stream()
                .map(codeSkill -> new skill(codeSkill.getCSId(), codeSkill.getCSName()))
                .collect(Collectors.toList());

        List<Tools> tools = toolsRepository.findByNameIn(ToolsNames).stream()
                .map(Tools -> new Tools(Tools.getId(), Tools.getName()))
                .collect(Collectors.toList());
        return new aiCardDTO(
                card.getECFromDate().toString(),
                card.getECToDate().toString(),
                card.getECTitle(),
                Arrays.asList(card.getECPosition().split(", ")),
                skills,
                tools,
                card.getECReflection(),
                card.getECImageUrl(),
                card.getECPdfName(),
                card.getECPdfUrl(),
                Arrays.asList(card.getECLink().split(", ")),
                card.getECSummary(),
                card.getECId()
        );
    }

    public List<aiCardDTO> toDTOList(List<ExperienceCard> experienceCards) {
        return experienceCards.stream()
                .map(CardMapper::toDTO)
                .collect(Collectors.toList());
    }
}
