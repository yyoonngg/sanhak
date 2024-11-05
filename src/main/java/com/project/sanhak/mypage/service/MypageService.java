package com.project.sanhak.mypage.service;

import com.project.sanhak.mypage.dto.changeRoadmapDTO;
import com.project.sanhak.mypage.dto.masteryDTO;
import com.project.sanhak.mypage.dto.quizDTO;
import com.project.sanhak.mypage.dto.roadmapDTO;
import com.project.sanhak.mypage.repository.MypageRepository;
import com.project.sanhak.mypage.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MypageService {
    @Autowired
    private MypageRepository mypageRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public void masterSkill(int uid, int msId) {
    }

    public quizDTO getQuiz(int msId) {
        return null;
    }

    public List<masteryDTO> getMasteryList(int uid, int csId) {
        return null;
    }

    public void updateRoadmap(int uid, int urId, List<changeRoadmapDTO> requestData) {
    }

    public void addNewRoadmap(int uid) {
    }

    public List<roadmapDTO> getRoadmapsByUid(int uid) {
        return null;
    }
}
