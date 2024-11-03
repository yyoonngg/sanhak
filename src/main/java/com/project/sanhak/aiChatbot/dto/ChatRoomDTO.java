package com.project.sanhak.aiChatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private int id;
    private int cardId;
    private String title;
    private String role;
}
