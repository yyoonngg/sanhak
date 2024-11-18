package com.project.sanhak.main.service;

import com.project.sanhak.card.repository.cardRepository;
import com.project.sanhak.domain.lounge.Lounges;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.domain.user.UserInfo;
import com.project.sanhak.lounge.repository.LoungeRepository;
import com.project.sanhak.main.dto.profileDTO;
import com.project.sanhak.main.repository.ProfileRepository;
import com.project.sanhak.main.repository.UserRepository;
import com.project.sanhak.mypage.repository.BadgeRepository;
import com.project.sanhak.mypage.repository.RoadmapRepository;
import com.project.sanhak.util.s3.S3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
public class ProfileService {
    @Autowired
    private MainService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private RoadmapRepository roadmapRepository;
    @Autowired
    private BadgeRepository badgeRepository;
    @Autowired
    private cardRepository cardRepository;
    @Autowired
    private LoungeRepository loungeRepository;
    @Autowired
    private S3FileService s3FileService;

    public profileDTO getProfile(int uid) {
        User user = userService.getUserFromUid(uid);
        UserInfo profile = profileRepository.findByUIuid(user);
        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "프로필을 찾을 수 없습니다.");
        }
        profileDTO profileDTO = new profileDTO();
        profileDTO.setId(profile.getUIid());
        profileDTO.setName(profile.getUIUserName());
        profileDTO.setEmail(profile.getUIUserEmail());
        profileDTO.setBio(profile.getUIBio());
        profileDTO.setProfileImgURL(profile.getUIProfileImg());
        profileDTO.setDesirePosition(profile.getUIDesirePosition());
        return profileDTO;
    }

    public void updateProfile(int uid, profileDTO profileDTO, MultipartFile imageFile) throws IOException {
        User user = userService.getUserFromUid(uid);
        UserInfo profile = profileRepository.findByUIuid(user);
        if (profile == null) {
            profile = new UserInfo();
            profile.setUIuid(user);
            Lounges lounge =new Lounges();
            lounge.setLUid(user);
            lounge.setLRoadmap(roadmapRepository.countByURuid(user));
            lounge.setLBadge(badgeRepository.countByUBUid(user));
            lounge.setLCard(cardRepository.countByECuid(user));
            lounge.setLView(0);
            lounge.setLLikes(0);
            loungeRepository.save(lounge);
        }
        profile.setUIUserName(profileDTO.getName());
        profile.setUIUserEmail(profileDTO.getEmail());
        profile.setUIBio(profileDTO.getBio());
        profile.setUIDesirePosition(profileDTO.getDesirePosition());
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = s3FileService.upload(imageFile);
            profile.setUIProfileImg(imageUrl);
        }
        Lounges lounge = loungeRepository.findByLUid(user);
        lounge.setLName(profileDTO.getName());
        lounge.setLPosition(profileDTO.getDesirePosition());
        lounge.setLImageURL(profileDTO.getProfileImgURL());
        profileRepository.save(profile);
    }
}
