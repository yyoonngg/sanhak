package com.project.sanhak.lounge.controller;

import com.project.sanhak.domain.user.User;
import com.project.sanhak.lounge.dto.LoungesDTO;
import com.project.sanhak.lounge.service.LoungeService;
import com.project.sanhak.main.service.MainService;
import com.project.sanhak.util.mail.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "${cors.allowed.origin}")
@RequestMapping("/api/lounge")
public class LoungeController {
    @Autowired
    private LoungeService loungeService;
    @Autowired
    private MainService userService;
    @Autowired
    private EmailService emailService;

    @Operation(summary = "Retrieve lounges with pagination and sorting options",
            description = "Fetches lounges based on the specified sort option and page number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved lounges",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoungesDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid sort or page option"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all/{sortOption}/{page}")
    public ResponseEntity<Page<LoungesDTO>> getAllLounges(
            @PathVariable int sortOption,
            @PathVariable int page,
            HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        Page<LoungesDTO> lounges = loungeService.getLounges(sortOption, page, uid);
        return ResponseEntity.ok(lounges);
    }


    //라운지 좋아요 로직
    @GetMapping("/like/{likeUid}")
    public ResponseEntity<?> likeLounge(@PathVariable int likeUid, HttpSession session){
        try {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;
            User user = userService.getUserFromUid(uid);
            User likeUser=userService.getUserFromUid(likeUid);
            loungeService.increaseLike(likeUser, user);
            return ResponseEntity.ok("{\"status\":\"success\"}");
        } catch(Exception e){
            log.error("서비스 실행 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }

    //라운지 조회수 로직
    @GetMapping("/view/{viewUid}")
    public ResponseEntity<?> viewLounge(@PathVariable int viewUid, HttpSession session){
        try {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;
            User user = userService.getUserFromUid(uid);
            User viewUser=userService.getUserFromUid(viewUid);
            loungeService.increaseView(viewUser, user);
            return ResponseEntity.ok("{\"status\":\"success\"}");
        } catch(Exception e){
            log.error("서비스 실행 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }


    @PostMapping("/send/")
    public ResponseEntity<?> sendMessage(@RequestParam String recipient,
                                         @RequestBody String contents,
                                         HttpSession session){
        try {
            Integer uidAttribute = (Integer) session.getAttribute("uid");
            if (uidAttribute == null) {
                throw new NullPointerException("UID is null");
            }
            int uid = uidAttribute;
            String sender = userService.getUserFromUid(uid).getUEmailId();
            try {
                emailService.sendMail(sender, recipient, contents);
                return ResponseEntity.ok("{\"status\":\"success\"}");
            } catch(Exception e){
                log.error("서비스 실행 중 오류 발생: {}", e.getMessage());
                return ResponseEntity.status(500).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
            }
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }
}