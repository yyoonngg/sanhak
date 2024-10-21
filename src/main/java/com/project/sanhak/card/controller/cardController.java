package com.project.sanhak.card.controller;

import com.project.sanhak.card.dto.aiCardDTO;
import com.project.sanhak.card.service.cardService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.project.sanhak.util.authUtil;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/card")
public class cardController {
    @Autowired
    private cardService cardService;

    @GetMapping("/")
    public List<aiCardDTO> getAiCard(@RequestHeader("Authorization") String token) throws JSONException {
        int uid = authUtil.getToken(token);
        return cardService.getAllMyCard(uid);
    }

}