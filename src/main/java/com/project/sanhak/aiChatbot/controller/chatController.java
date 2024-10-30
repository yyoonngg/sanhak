package com.project.sanhak.aiChatbot.controller;

import com.project.sanhak.card.repository.cardRepository;
import com.project.sanhak.aiChatbot.service.chatService;
import com.project.sanhak.domain.chat.ChatMessage;
import com.project.sanhak.domain.chat.ChatRooms;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.main.service.MainService;
import com.project.sanhak.util.s3.S3FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/chat")
public class chatController {
    @Autowired
    private MainService userService;
    @Autowired
    private chatService chatService;

    @GetMapping("initialize/{chat_id}/{chat_type}")
    public ResponseEntity<?> handshake(Authentication authentication,
                                       @PathVariable(required = false) Integer chat_id,
                                       @PathVariable(required = false) Integer chat_type) {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Integer uidAttribute = oAuth2User.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;
            User user = userService.getUserFromUid(uid);
            // chat_id 또는 chat_type이 null일 경우 가장 최근 채팅방 조회
            if (chat_id == null || chat_type == null) {
                ChatRooms latestChatRoom = chatService.getLatestChatRoomByUserId(user);
                if (latestChatRoom == null) {
                    return ResponseEntity.badRequest().body("최근 채팅방이 존재하지 않습니다.");
                }
                chat_id = latestChatRoom.getCRId();
                chat_type = latestChatRoom.getCRType();
            }

            // 초기화
            String response = chatService.initializeChat(uid, chat_id, chat_type);
            if ("success".equals(response)) {
                return ResponseEntity.ok("채팅이 성공적으로 초기화되었습니다.");
            } else {
                return ResponseEntity.badRequest().body("Python 서버 요청 실패: " + response);
            }
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("오류가 발생했습니다: UID를 가져올 수 없습니다.");
        } catch (Exception e) {
            log.error("채팅 초기화 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }


    // 사용자의 채팅방 목록을 가져오는 메서드
    @GetMapping("/list")
    public ResponseEntity<?> callChatList(Authentication authentication) {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Integer uidAttribute = oAuth2User.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;

            // 사용자의 채팅방 목록 조회
            List<ChatRooms> chatRoomsList = chatService.getUserChatRooms(uid);
            return ResponseEntity.ok(chatRoomsList);

        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("오류가 발생했습니다: UID를 가져올 수 없습니다.");
        } catch (Exception e) {
            log.error("채팅방 목록 조회 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }

    // 특정 채팅방의 과거 메시지를 가져오는 메서드
    @GetMapping("/message/{chat_id}")
    public ResponseEntity<?> callChatMessage(Authentication authentication,
                                             @PathVariable(required = false) Integer chat_id) {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Integer uidAttribute = oAuth2User.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;

            // 채팅방 메시지 조회
            List<ChatMessage> chatMessages = chatService.getChatMessages(chat_id, uid);
            return ResponseEntity.ok(chatMessages);

        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("오류가 발생했습니다: UID를 가져올 수 없습니다.");
        } catch (Exception e) {
            log.error("채팅 메시지 조회 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }

    @Transactional
    @PostMapping("/{chat_id}/send/{chat_type}")
    public ResponseEntity<?> chatToBot(Authentication authentication,
                                       @PathVariable int chat_id,
                                       @PathVariable int chat_type,
                                       @RequestBody Map<String, String> requestData) {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Integer uidAttribute = oAuth2User.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;

            String question = requestData.get("question");
            if (question == null || question.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("질문이 비어 있습니다.");
            }

            // Service 계층에 메시지를 전송하고 저장하도록 요청
            String response = chatService.sendMessageToBot(uid, chat_id, chat_type, question);
            return ResponseEntity.ok(response);

        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("오류가 발생했습니다: UID를 가져올 수 없습니다.");
        } catch (Exception e) {
            log.error("채팅 전송 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }


}