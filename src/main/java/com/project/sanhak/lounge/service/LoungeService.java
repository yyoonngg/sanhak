package com.project.sanhak.lounge.service;

import com.project.sanhak.domain.lounge.LoungeLikes;
import com.project.sanhak.domain.lounge.LoungeView;
import com.project.sanhak.domain.lounge.Lounges;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.lounge.dto.LoungesDTO;
import com.project.sanhak.lounge.repository.LoungeLikesRepository;
import com.project.sanhak.lounge.repository.LoungeRepository;
import com.project.sanhak.lounge.repository.LoungeViewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class LoungeService {
    @Autowired
    private LoungeRepository loungeRepository;
    @Autowired
    private LoungeViewRepository loungeViewRepository;
    @Autowired
    private LoungeLikesRepository loungeLikesRepository;
    public void increaseRnum(User user) {
        Lounges lounge = loungeRepository.findByLUid(user);
        if (lounge != null) {
            lounge.setLRoadmap(lounge.getLRoadmap() + 1);
            loungeRepository.save(lounge);
        } else {
            throw new EntityNotFoundException("해당 사용자의 라운지를 찾을 수 없습니다.");
        }
    }

    public void increaseBnum(User user) {
        Lounges lounge = loungeRepository.findByLUid(user);
        if (lounge != null) {
            lounge.setLRoadmap(lounge.getLBadge() + 1);
            loungeRepository.save(lounge);
        } else {
            throw new EntityNotFoundException("해당 사용자의 라운지를 찾을 수 없습니다.");
        }
    }

    public void increaseCnum(User user) {
        Lounges lounge = loungeRepository.findByLUid(user);
            if (lounge != null) {
                lounge.setLRoadmap(lounge.getLCard() + 1);
                loungeRepository.save(lounge);
            } else {
                throw new EntityNotFoundException("해당 사용자의 라운지를 찾을 수 없습니다.");
        }
    }

    public void increaseView(User user) {
        Lounges lounge = loungeRepository.findByLUid(user);
        LoungeView view = loungeViewRepository.findByLVlid(lounge);
        if (lounge != null) {
            if(view == null){
                view = new LoungeView();
                view.setLVuid(user);
                view.setLVlid(lounge);
                loungeViewRepository.save(view);
                lounge.setLRoadmap(lounge.getLView() + 1);
                loungeRepository.save(lounge);
            }
        } else {
            throw new EntityNotFoundException("해당 사용자의 라운지를 찾을 수 없습니다.");
        }

    }

    public void increaseLike(User user){
        Lounges lounge = loungeRepository.findByLUid(user);
        LoungeLikes like = loungeLikesRepository.findByLLlid(lounge);
        if (lounge != null) {
            if(like != null){
                loungeLikesRepository.delete(like);
                lounge.setLRoadmap(lounge.getLLikes() - 1);
                loungeRepository.save(lounge);
            }else{
                like=new LoungeLikes();
                like.setLLuid(user);
                like.setLLlid(lounge);
                loungeLikesRepository.save(like);
                lounge.setLRoadmap(lounge.getLLikes() + 1);
                loungeRepository.save(lounge);
            }
        } else {
            throw new EntityNotFoundException("해당 사용자의 라운지를 찾을 수 없습니다.");
        }
    }

    public Page<LoungesDTO> getLounges(int sortOption, int page) {
        Sort sort = switch (sortOption) {
            case 2 -> Sort.by(Sort.Direction.DESC, "badgeCount"); // 뱃지 많은 순
            case 3 -> Sort.by(Sort.Direction.DESC, "roadmapCount"); // 로드맵 많은 순
            case 4 -> Sort.by(Sort.Direction.DESC, "cardCount"); // 카드 많은 순
            case 5 -> Sort.by(Sort.Direction.DESC, "likeCount"); // 좋아요 순
            case 6 -> Sort.by(Sort.Direction.DESC, "viewCount"); // 조회수 순
            default -> Sort.by(Sort.Direction.DESC, "createdDate"); // 최신순
        };

        PageRequest pageRequest = PageRequest.of(page - 1, 20, sort);
        Page<Lounges> loungesPage = loungeRepository.findAll(pageRequest);
        return loungesPage.map(this::convertToDTO);
    }

    private LoungesDTO convertToDTO(Lounges lounge) {
        LoungesDTO dto = new LoungesDTO();
        dto.setId(lounge.getLId());
        dto.setLikes(lounge.getLLikes());
        dto.setViewCount(lounge.getLView());
        dto.setBadgeCount(lounge.getLBadge());
        dto.setRoadmapCount(lounge.getLRoadmap());
        dto.setCardCount(lounge.getLCard());
        dto.setName(lounge.getLName());
        dto.setPosition(lounge.getLPosition());
        dto.setImageURL(lounge.getLImageURL());
        return dto;
    }

}
