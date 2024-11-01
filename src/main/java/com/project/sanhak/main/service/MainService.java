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
    public void saveUserWithGitUid(String email) {
        // `oauth_token` 테이블에서 email을 기반으로 gitUid 조회
        Optional<OAuthToken> oauthTokenOpt = oAuthTokenRepository.findByEmail(email);
        if (oauthTokenOpt.isPresent()) {

            // `User` 객체 생성 및 저장
            User user = new User();
            user.setUGitId(email);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("해당 email을 가진 OAuthToken이 존재하지 않습니다.");
        }
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName(); // 인증된 사용자의 이메일 가져오기

            // 이메일을 기반으로 User를 조회
            Optional<User> userOpt = userRepository.findByUGitId(email);
            if (userOpt.isEmpty()) {
                // 사용자가 존재하지 않으면 User DB에 저장
                saveUserWithGitUid(email);
                // 다시 조회
                userOpt = userRepository.findByUGitId(email);
            }

            return userOpt.orElseThrow(() -> new IllegalArgumentException("현재 사용자를 찾을 수 없습니다."));
        } else {
            throw new IllegalStateException("사용자가 인증되지 않았습니다.");
        }
    }


}
