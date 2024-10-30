package com.project.sanhak.aiChatbot.mapper;

import com.project.sanhak.aiChatbot.dto.aiChatDTO;
import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.domain.card.ExperienceCard;
import com.project.sanhak.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ChatMapper {

    public Map<String, Object> toChatInitData(int userId, aiCardDTO cardData, int chatModelType) {
        Map<String, Object> chatInitData = new HashMap<>();
        chatInitData.put("userId", userId);
        chatInitData.put("chatModel", String.valueOf(chatModelType));

        Map<String, String> userInput = new HashMap<>();
        userInput.put("title", cardData.getTitle());
        userInput.put("tool", String.join(", ", cardData.getTools()));
        userInput.put("position", String.join(", ", cardData.getCategory()));
        userInput.put("reflection", cardData.getReflection());
        userInput.put("pdfText", cardData.getPdfUrl());

        chatInitData.put("userInput", userInput);
        return chatInitData;
    }
}

