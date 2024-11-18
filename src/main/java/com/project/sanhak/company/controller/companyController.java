package com.project.sanhak.company.controller;

import com.project.sanhak.company.dto.companyDTO;
import com.project.sanhak.company.service.companyService;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.main.service.MainService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "${cors.allowed.origin}")
@RequestMapping("/api/company")
public class companyController {
    @Autowired
    private companyService companyService;
    @Autowired
    private MainService userService;

    // 회사 추천 시스템
    // 내가 익힌 스킬을 모조리 모아서 던져주기.
    @GetMapping("/recomand, /recommand/{uid}")
    public ResponseEntity<?> getAiCard(HttpSession session,
                                       @PathVariable(required = false) Integer uid) {
        try {
            if (uid == null) {
                Integer uidAttribute = (Integer) session.getAttribute("uid");
                if (uidAttribute == null) {
                    throw new NullPointerException("UID is null");
                }
                uid = uidAttribute;
            }
            User user = userService.getUserFromUid(uid);
            List<companyDTO> companyList =companyService.recommandCompany(user);
            return ResponseEntity.status(200).body("");
        } catch (Exception e) {
            log.error("서비스 실행 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }
}