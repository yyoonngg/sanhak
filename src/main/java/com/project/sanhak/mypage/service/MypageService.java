package com.project.sanhak.mypage.service;

import com.project.sanhak.mypage.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MypageService {
    @Autowired
    private MypageRepository mypageRepository;

    @Autowired
    private NotificationRepository notificationRepository;

}
