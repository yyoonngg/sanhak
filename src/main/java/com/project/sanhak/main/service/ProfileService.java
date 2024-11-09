package com.project.sanhak.main.service;

import com.project.sanhak.main.dto.profileDTO;
import com.project.sanhak.domain.user.Profile;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.main.repository.UserRepository;
import com.project.sanhak.main.repository.ProfileRepository;
import com.project.sanhak.util.s3.S3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Service
public class ProfileService {

    @Autowired
    private static UserRepository userRepository;

    @Autowired
    private static ProfileRepository profileRepository;

    @Autowired
    private static S3FileService s3FileService;

    public static profileDTO getProfile(int uid) {
        Profile profile = profileRepository.findByPRuid_UId(uid);
        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "프로필을 찾을 수 없습니다.");
        }
        profileDTO profileDTO = new profileDTO();
        profileDTO.setId(profile.getPRid().intValue());
        profileDTO.setName(profile.getPRname());
        profileDTO.setEmail(profile.getPRemail());
        profileDTO.setBio(profile.getPRbio());
        profileDTO.setProfileImgURL(profile.getPRprofileImgURL());
        return profileDTO;
    }

    public static void updateProfile(int uid, profileDTO profileDTO, MultipartFile imageFile) throws IOException {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        Profile profile = profileRepository.findByPRuid_UId(uid);
        if (profile == null) {
            profile = new Profile();
            profile.setPRuid(user);
        }
        profile.setPRname(profileDTO.getName());
        profile.setPRemail(profileDTO.getEmail());
        profile.setPRbio(profileDTO.getBio());

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = s3FileService.upload(imageFile);
            profile.setPRprofileImgURL(imageUrl);
        }
        profileRepository.save(profile);
    }
}
