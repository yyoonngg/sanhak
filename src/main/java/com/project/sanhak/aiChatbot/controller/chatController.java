package com.project.sanhak.aiChatbot.controller;

import com.project.sanhak.aiChatbot.dto.ChatRoomDTO;
import com.project.sanhak.aiChatbot.service.chatService;
import com.project.sanhak.domain.chat.ChatMessage;
import com.project.sanhak.domain.chat.ChatRooms;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.main.service.MainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "${cors.allowed.origin}")
@RequestMapping("/api/chat")
public class chatController {
    @Autowired
    private MainService userService;
    @Autowired
    private chatService chatService;

    @Operation(summary = "채팅방 초기화")
    @ApiResponse(responseCode = "200", description = "채팅방 초기화 성공 메시지")
    @GetMapping("initialize/{chat_id}/{chat_type}")
    public ResponseEntity<?> handshake(HttpSession session,
                                       @PathVariable(required = false) Integer chat_id,
                                       @PathVariable(required = false) Integer chat_type) {
        try {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
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

            String response = chatService.initializeChat(uid, chat_id, chat_type);
            if ("success".equals(response)) {
                System.out.println(chat_type+"로 초기화");
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


    @Operation(summary = "사용자의 채팅방 목록을 가져옴")
    @ApiResponse(responseCode = "200", description = "사용자 채팅방 목록",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChatRoomDTO.class))))
    @GetMapping("/list")
    public ResponseEntity<?> callChatList(HttpSession session) {
        try {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;

            // 사용자의 채팅방 목록 조회 (DTO로 변환하여 title 포함)
            List<ChatRoomDTO> chatRoomsList = chatService.getUserChatRooms(uid);
            return ResponseEntity.ok(chatRoomsList);

        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("오류가 발생했습니다: UID를 가져올 수 없습니다.");
        } catch (Exception e) {
            log.error("채팅방 목록 조회 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }

    @Operation(summary = "특정 채팅방의 과거 메세지 호출")
    @ApiResponse(responseCode = "200", description = "특정 채팅방의 과거 메세지 목록",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChatMessage.class))))
    @GetMapping("/message/{chat_id}")
    public ResponseEntity<?> callChatMessage(HttpSession session,
                                             @PathVariable(required = false) Integer chat_id) {
        try {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
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

    @Operation(summary = "AI 서버와의 채팅 메시지 송신 및 응답")
    @ApiResponse(responseCode = "200", description = "AI 서버 응답",
            content = @Content(schema = @Schema(example = "{\"response\": \"AI 응답 메시지\"}")))
    @Transactional
    @PostMapping("/{chat_id}/send/{chat_type}")
    public ResponseEntity<?> chatToBot(HttpSession session,
                                       @PathVariable int chat_id,
                                       @PathVariable int chat_type,
                                       @RequestBody Map<String, String> requestData) {
        try {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;
            System.out.println(chat_type+"와 대화");
            String question = requestData.get("question");
            if (question == null || question.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("질문이 비어 있습니다.");
            }

            String response = chatService.sendMessageToBot(uid, chat_id, chat_type, question);
            return ResponseEntity.ok(Map.of("response", response));

        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("오류가 발생했습니다: UID를 가져올 수 없습니다.");
        } catch (Exception e) {
            log.error("채팅 전송 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }
}