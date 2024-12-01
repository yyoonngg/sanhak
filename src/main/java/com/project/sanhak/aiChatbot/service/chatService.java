package com.project.sanhak.aiChatbot.service;

import com.project.sanhak.aiChatbot.dto.ChatRoomDTO;
import com.project.sanhak.aiChatbot.mapper.ChatMapper;
import com.project.sanhak.aiChatbot.repository.chatRepository;
import com.project.sanhak.aiChatbot.repository.messageRepository;
import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.card.service.cardService;
import com.project.sanhak.domain.chat.ChatMessage;
import com.project.sanhak.domain.chat.ChatRooms;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.util.s3.S3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class chatService {
    @Value("${api.base.url}")
    private String apiBaseUrl;
    @Autowired
    private cardService cardService;
    @Autowired
    private chatRepository chatRepository;
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private messageRepository messageRepository;
    @Autowired
    private S3FileService S3FileService;
    @Autowired
    private RestTemplate restTemplate;

    private final WebClient webClient;

    public chatService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String initializeChat(int uid, int chat_id, int chat_type) throws IOException {
        int card_id = getTargetChatCard(chat_id);
        aiCardDTO cardData = cardService.getTargetCard(card_id);

        if (cardData == null) {
            return "해당 카드가 존재하지 않습니다.";
        }

        MultipartFile pdfFile = S3FileService.downloadFileAsMultipartFile(cardData.getPdfUrl());
        String pdfText = cardService.extractTextFromPDF(pdfFile);

        // Python 서버로 전달할 데이터 생성
        Map<String, Object> chatInitData = new HashMap<>();
        chatInitData.put("userId", String.valueOf(uid));  // userId를 String으로 변환하여 전달
        chatInitData.put("chatModel", String.valueOf(chat_type));  // chatModel을 String으로 설정

        // UserInput 생성
        Map<String, Object> userInput = new HashMap<>();
        userInput.put("title", cardData.getTitle());
        userInput.put("reflection", cardData.getReflection());

        // tools를 리스트의 문자열로 변환
        List<String> toolsList = cardData.getTools().stream()
                .map(tool -> tool.getName())  // Tool의 이름만 리스트에 담음
                .collect(Collectors.toList());
        userInput.put("tool", toolsList);

        // position을 리스트로 설정 (단일 항목이어도 리스트 형식 유지)
        List<String> positionList = Collections.singletonList(cardData.getCategory().toString());
        userInput.put("position", positionList);

        userInput.put("pdfText", pdfText);

        // chatInitData에 UserInput 추가
        chatInitData.put("UserInput", userInput);

        String url = apiBaseUrl + "/initialize";
        ResponseEntity<String> response = restTemplate.postForEntity(url, chatInitData, String.class);

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
    public List<ChatRoomDTO> getUserChatRooms(int uid) {

        return chatRepository.findByCRuid_UId(uid)
                .stream()
                .filter(chatRoom -> chatRoom.getCRType() == 0)  // `crType`이 0인 것만 필터링
                .map(chatRoom -> new ChatRoomDTO(
                        chatRoom.getCRId(),
                        chatRoom.getCRecid().getECId(),
                        chatRoom.getCRecid().getECTitle(),
                        "AI 면접관"
                ))
                .collect(Collectors.toList());
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
        chatRequestData.put("userId", String.valueOf(uid));  // userId를 String 형식으로 전송
        chatRequestData.put("question", question);

        // chat_type에 따라 다른 URL 설정
        String url;
        switch (chat_type) {
            case 0:
                url = apiBaseUrl + "/generalAi";
                break;
            case 1:
                url = apiBaseUrl + "/interviewAi";
                break;
            case 2:
                url = apiBaseUrl + "/introduceAi";
                break;
            default:
                throw new IllegalArgumentException("유효하지 않은 chat_type입니다: " + chat_type);
        }

        try {
            // 요청을 전송하고 응답을 처리
            Map<String, Object> response = webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(chatRequestData)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            // 응답에서 'response' 필드 처리
            if (response != null && response.containsKey("response")) {
                String botResponse = (String) response.get("response");

                // 채팅 메시지 DB에 저장
                saveChatMessage(chatRoom, question, true);   // 유저 메시지 저장
                saveChatMessage(chatRoom, botResponse, false);  // 봇 응답 메시지 저장

                return botResponse;
            } else {
                throw new IllegalStateException("Python 서버 응답에 'response' 필드가 없습니다.");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Python 서버 요청 실패: " + e.getMessage(), e);
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