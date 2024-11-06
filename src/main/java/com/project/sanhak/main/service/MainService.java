package com.project.sanhak.main.service;

import com.project.sanhak.domain.user.User;
import com.project.sanhak.main.dto.cardDTO;
import com.project.sanhak.main.dto.profileDTO;
import com.project.sanhak.main.dto.rankDTO;
import com.project.sanhak.main.repository.OAuthTokenRepository;
import com.project.sanhak.main.repository.UserInfoRepository;
import com.project.sanhak.main.repository.UserRepository;
import com.project.sanhak.mypage.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        System.out.println("4");
        return userRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    public void updateProfile(int uid, profileDTO profile, MultipartFile imageFile) {
    }

    public profileDTO getProfile(Integer uid) {
        return null;
    }

    public List<cardDTO> getCardList(String sort) {
        return null;
    }

    public List<rankDTO> getRankList(String sort) {
        return null;
    }
}
