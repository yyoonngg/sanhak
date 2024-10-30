package com.project.sanhak.aiChatbot.service;

import com.project.sanhak.aiChatbot.mapper.ChatMapper;
import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.card.service.cardService;
import com.project.sanhak.aiChatbot.repository.*;
import com.project.sanhak.domain.chat.ChatMessage;
import com.project.sanhak.domain.chat.ChatRooms;
import com.project.sanhak.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class chatService {
    @Autowired
    private cardService cardService;
    @Autowired
    private chatRepository chatRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private messageRepository messageRepository;

    public String initializeChat(int uid, int chat_id, int chat_type) {
        int card_id = getTargetChatCard(chat_id);
        aiCardDTO cardData = cardService.getTargetCard(card_id);
        if (cardData == null) {
            return "해당 카드가 존재하지 않습니다.";
        }
        Map<String, Object> chatInitData = chatMapper.toChatInitData(uid, cardData, chat_id);
        String pythonServerUrl = "http://python-server-url/initialize";
        ResponseEntity<String> response = restTemplate.postForEntity(pythonServerUrl, chatInitData, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "success";
        } else {
            return response.getBody();
        }
    }

    public int getTargetChatCard(int chat_id) {
        return chatRepository.findCRecIdByCRId(chat_id);
    }

    public ChatRooms getLatestChatRoomByUserId(User user) {
        // 최신 채팅방 조회 로직을 chatRepository를 사용해 구현
        return chatRepository.findTopByCRuidOrderByCRIdDesc(user);
    }

    // 사용자별 채팅방 목록을 조회하는 메서드
    public List<ChatRooms> getUserChatRooms(int uid) {
        return chatRepository.findByCRuid_UId(uid);
    }

    // 특정 채팅방의 메시지를 조회하는 메서드
    public List<ChatMessage> getChatMessages(int chat_id, int uid) {
        // 사용자가 해당 채팅방에 접근할 권한이 있는지 확인
        ChatRooms chatRoom = chatRepository.findByCRIdAndCRuid_UId(chat_id, uid)
                .orElseThrow(() -> new IllegalArgumentException("해당 채팅방에 접근 권한이 없습니다."));
        return messageRepository.findByCMcrid_CRIdOrderByCMTimeAsc(chat_id);
    }

    public String sendMessageToBot(int uid, int chat_id, int chat_type, String question) {
        // 채팅방 조회
        ChatRooms chatRoom = chatRepository.findById(chat_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다: " + chat_id));

        // 요청 데이터 생성
        Map<String, Object> chatRequestData = new HashMap<>();
        chatRequestData.put("userId", uid);
        chatRequestData.put("question", question);

        String pythonServerUrl = "http://python-server-url/send";
        ResponseEntity<Map> response = restTemplate.postForEntity(pythonServerUrl, chatRequestData, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map responseBody = response.getBody();
            if (responseBody.containsKey("response")) {
                String botResponse = (String) responseBody.get("response");

                // 채팅 메시지 DB 저장
                saveChatMessage(chatRoom, question, true);  // 유저 메시지 저장
                saveChatMessage(chatRoom, botResponse, false);  // 봇 응답 메시지 저장

                return botResponse;
            } else {
                throw new IllegalStateException("Python 서버 응답에 'response' 필드가 없습니다.");
            }
        } else {
            throw new IllegalStateException("Python 서버 요청 실패: " + response.getStatusCode());
        }
    }

    // 채팅 메시지 저장
    private void saveChatMessage(ChatRooms chatRoom, String content, boolean isUser) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setCMcrid(chatRoom);
        chatMessage.setCMContent(content);
        chatMessage.setCMTime(LocalDateTime.now());
        chatMessage.setCMIsUser(isUser ? 1 : 0); // 1: 사용자 메시지, 0: 봇 응답
        messageRepository.save(chatMessage);
    }
}