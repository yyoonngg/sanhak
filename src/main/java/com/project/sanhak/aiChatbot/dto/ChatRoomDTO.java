package com.project.sanhak.aiChatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description ="채팅방")
public class ChatRoomDTO {
    private int id;
    private int cardId;
    private String title;
    private String role;
}
