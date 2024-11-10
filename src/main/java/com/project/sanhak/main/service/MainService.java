package com.project.sanhak.main.service;

import com.project.sanhak.domain.user.OAuthToken;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.main.repository.OAuthTokenRepository;
import com.project.sanhak.main.repository.UserInfoRepository;
import com.project.sanhak.main.repository.UserRepository;
import com.project.sanhak.mypage.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public User getUserFromUid(int uid) {
        return userRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    // 추가된 메서드: email을 기반으로 gitUid 조회 후 User DB에 저장


}
