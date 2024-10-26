package com.project.sanhak.main.service;

import com.project.sanhak.domain.user.User;
import com.project.sanhak.main.repository.*;
import com.project.sanhak.mypage.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private OAuthTokenRepository oAuthTokenRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public User getUserFromToken(String token) {
        User user = null;
        return user;
    }
}
