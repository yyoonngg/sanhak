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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public void increaseView(User viewUser, User user) {
        Lounges lounge = loungeRepository.findByLUid(viewUser);
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

    public void increaseLike(User likeUser, User user){
        Lounges lounge = loungeRepository.findByLUid(likeUser);
        LoungeLikes like = loungeLikesRepository.findByLLlidAndLLuid(lounge, user);
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
            case 2 -> Sort.by(Sort.Direction.DESC, "LBadge"); // 뱃지 많은 순
            case 3 -> Sort.by(Sort.Direction.DESC, "LRoadmap"); // 로드맵 많은 순
            case 4 -> Sort.by(Sort.Direction.DESC, "LCard"); // 카드 많은 순
            case 5 -> Sort.by(Sort.Direction.DESC, "LLikes"); // 좋아요 순
            case 6 -> Sort.by(Sort.Direction.DESC, "LView"); // 조회수 순
            default -> Sort.by(Sort.Direction.DESC, "LId"); // 최신순
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
        dto.setBadge_cnt(lounge.getLBadge());
        dto.setRoadmap_cnt(lounge.getLRoadmap());
        dto.setCard_cnt(lounge.getLCard());
        dto.setName(lounge.getLName());
        dto.setCategory(lounge.getLPosition());
        dto.setImageURL(lounge.getLImageURL());
        return dto;
    }

}
