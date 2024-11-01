package com.project.sanhak.main.controller;

import com.project.sanhak.main.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class MainController {
    @Autowired
    private MainService mainService;
    @PostMapping("/login/saveUser")
    public String saveUser(@RequestParam String email) {
        try {
            mainService.saveUserWithGitUid(email);
            return "User 저장 성공!";
        } catch (IllegalArgumentException e) {
            return "오류: " + e.getMessage();
        }
    }
}