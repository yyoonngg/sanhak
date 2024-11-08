package com.project.sanhak.mypage.service;

import com.project.sanhak.category.repository.categoryRepository;
import com.project.sanhak.domain.skil.code.CodeSkil;
import com.project.sanhak.domain.skil.code.MasterySkil;
import com.project.sanhak.domain.skil.user.*;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.mypage.dto.*;
import com.project.sanhak.mypage.repository.*;
import com.project.sanhak.main.service.MainService;
import com.project.sanhak.category.service.categoryService;
import com.project.sanhak.category.repository.masteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class MypageService {
    @Value("${api.base.url}")
    private String apiBaseUrl;
    @Autowired
    private MypageRepository mypageRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private RoadmapRepository roadmapRepository;
    @Autowired
    private RoadmapSkilRepository roadmapSkilRepository;
    @Autowired
    private RoadmapSkilPrequeRepository roadmapSkilPrequeRepository;
    @Autowired
    private UserMasterySkilRepository userMasterySkilRepository;
    @Autowired
    private masteryRepository masteryRepository;
    @Autowired
    private categoryRepository codeSkilRepository;
    @Autowired
    private MainService userService;
    @Autowired
    private categoryService categoryService;
    private final WebClient webClient;
    public MypageService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void masterSkill(int uid, int msId) {
        User user = userService.getUserFromUid(uid);
        MasterySkil mastery=masteryRepository.findByMSId(msId);
        boolean alreadyMastered = userMasterySkilRepository.existsByUMSuidAndUMSmsid(user, mastery);
        if (alreadyMastered) {
            throw new IllegalStateException("이미 익힌 스킬입니다.");
        }
        UserMasterySkil userMasterySkil = new UserMasterySkil();
        userMasterySkil.setUMSuid(user);
        userMasterySkil.setUMSmsid(mastery);
        userMasterySkilRepository.save(userMasterySkil);
    }

    public quizDTO getQuiz(int msId) {
        //public List<quizDTO> getQuiz(int msId) {
        String url = apiBaseUrl +"/quiz";
        MasterySkil mastery = masteryRepository.findByMSId(msId);
        List<String> masteryInfoList = List.of(
                mastery.getMSInfo1(),
                mastery.getMSInfo2(),
                mastery.getMSInfo3()
        );
        Map<String, Object> quizRequestData = new HashMap<>();
        Optional<CodeSkil> codeSkillOptional =codeSkilRepository.findById(mastery.getMSCSid().getCSId());
        quizRequestData.put("language", codeSkillOptional.map(CodeSkil::getCSName));
        quizRequestData.put("main", mastery.getMSName());
        quizRequestData.put("sub",masteryInfoList);
        try {
            // 요청을 전송하고 응답을 처리
            Map<String, Object> response = webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(quizRequestData)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            if (response != null && response.containsKey("question") &&
                    response.containsKey("options") && response.containsKey("answer")) {
                String question = (String) response.get("question");
                List<String> options = (List<String>) response.get("options");
                int answer = (int) response.get("answer");
                return new quizDTO(question, options, answer);


            }
            //List<quizDTO> quizList = new ArrayList<>();
            //if (responseList != null) {
                //for (Map<String, Object> response : responseList) {
                    //if (response.containsKey("question") && response.containsKey("options") && response.containsKey("answer")) {
                        //String question = (String) response.get("question");
                        //List<String> options = (List<String>) response.get("options");
                        //int answer = (int) response.get("answer");

                        //quizList.add(new quizDTO(question, options, answer));
                    //}
            else {
                throw new IllegalStateException("Python 서버 응답에 필요한 'question', 'options', 'answer' 필드가 없습니다.");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Python 서버 요청 실패: " + e.getMessage(), e);
        }
    }


    public List<masteryDTO> getMasteryList(int uid, int csId) {
        User user = userService.getUserFromUid(uid);
        CodeSkil codeSkil = categoryService.getCodeSkilFromCSId(csId);
        List<MasterySkil> masterySkills = masteryRepository.findByMSCSid(codeSkil);
        List<masteryDTO> masteryList = new ArrayList<>();
        for (MasterySkil masterySkill : masterySkills) {
            boolean state=userMasterySkilRepository.findByUMSuidAndUMSmsid(user, masterySkill);
            masteryDTO dto = new masteryDTO();
            dto.setId(masterySkill.getMSId());
            dto.setName(masterySkill.getMSName());
            dto.setInfo1(masterySkill.getMSInfo1());
            dto.setInfo2(masterySkill.getMSInfo2());
            dto.setInfo3(masterySkill.getMSInfo3());
            dto.setCs_id(csId);
            dto.setState(state);
            masteryList.add(dto);
        }
        return masteryList;
    }

    public void updateRoadmap(int urId, List<changeRoadmapDTO> requestData) {
        // 로드맵 가져오기
        Optional<UserRoadmap> userRoadmapOptional = roadmapRepository.findById(urId);
        if (userRoadmapOptional.isEmpty()) {
            throw new IllegalArgumentException("로드맵을 찾을 수 없습니다.");
        }
        UserRoadmap userRoadmap = userRoadmapOptional.get();

        // 각 요청 데이터 처리
        for (changeRoadmapDTO dto : requestData) {
            if (dto.getState() == 1) { // 추가 상태
                if (dto.getId() != 0) { // 부모-자식 관계 (라인)
                    int parentId = dto.getMapping().get(0);
                    int childId = dto.getMapping().get(1);
                    Optional<UserRoadmapSkil> parentSkill = roadmapSkilRepository.findById(parentId);
                    Optional<UserRoadmapSkil> childSkill = roadmapSkilRepository.findById(childId);

                    if (parentSkill.isPresent() && childSkill.isPresent()) {
                        UserRoadmapSkilPreque preque = new UserRoadmapSkilPreque();
                        preque.setURSPparentscsid(parentSkill.get());
                        preque.setURSPchildcsid(childSkill.get());
                        roadmapSkilPrequeRepository.save(preque);
                    } else {
                        throw new IllegalArgumentException("부모 또는 자식 스킬을 찾을 수 없습니다.");
                    }
                } else { // 노드 (x, y 좌표)
                    int x = dto.getMapping().get(0);
                    int y = dto.getMapping().get(1);

                    UserRoadmapSkil skill = new UserRoadmapSkil();
                    skill.setURSurid(userRoadmap);
                    skill.setURScsid(dto.getId());
                    skill.setURScsName("스킬 이름"); // 실제 이름은 필요에 따라 설정
                    skill.setURScsX(x);
                    skill.setURScsY(y);
                    roadmapSkilRepository.save(skill);
                }
            } else if (dto.getState() == 0) { // 삭제 상태
                if (dto.getId() == 0) { // 부모-자식 관계 (라인)
                    int parentId = dto.getMapping().get(0);
                    int childId = dto.getMapping().get(1);
                    Optional<UserRoadmapSkil> parentOptional=roadmapSkilRepository.findById(parentId);
                    Optional<UserRoadmapSkil> childOptional=roadmapSkilRepository.findById(childId);
                    UserRoadmapSkil parent=parentOptional.get();
                    UserRoadmapSkil child=childOptional.get();
                    roadmapSkilPrequeRepository.deleteByURSPparentscsidAndURSPchildcsid(parent, child);
                } else { // 노드 (x, y 좌표)
                    int skillId = dto.getId();
                    roadmapSkilRepository.deleteById(skillId);
                }
            }
        }
    }

    public void addNewRoadmap(int uid) {
        UserRoadmap roadmap = new UserRoadmap();
        roadmap.setURuid(userService.getUserFromUid(uid));
        roadmap.setState(0);
        roadmapRepository.save(roadmap);
    }

    public List<roadmapDTO> getRoadmapsByUid(int uid) {
        return null;
    }
}
