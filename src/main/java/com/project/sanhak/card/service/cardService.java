package com.project.sanhak.card.service;

import com.project.sanhak.card.repository.cardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class cardService {
    @Autowired
    private cardRepository cardRepository;
}