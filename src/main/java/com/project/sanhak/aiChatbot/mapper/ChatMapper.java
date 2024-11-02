package com.project.sanhak.aiChatbot.mapper;

import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.domain.skil.code.Tools;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
        String toolNames = cardData.getTools().stream()
                .map(Tools::getName)
                .collect(Collectors.joining(", "));
        userInput.put("tool", toolNames);
        userInput.put("position", String.join(", ", cardData.getCategory()));
        userInput.put("reflection", cardData.getReflection());
        userInput.put("pdfText", cardData.getPdfUrl());

        chatInitData.put("userInput", userInput);
        return chatInitData;
    }
}

