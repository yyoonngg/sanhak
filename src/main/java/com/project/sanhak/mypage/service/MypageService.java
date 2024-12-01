package com.project.sanhak.mypage.service;

import com.project.sanhak.category.repository.categoryRepository;
import com.project.sanhak.category.repository.masteryRepository;
import com.project.sanhak.category.service.categoryService;
import com.project.sanhak.domain.skil.code.CodeSkil;
import com.project.sanhak.domain.skil.code.MasterySkil;
import com.project.sanhak.domain.skil.user.UserMasterySkil;
import com.project.sanhak.domain.skil.user.UserRoadmap;
import com.project.sanhak.domain.skil.user.UserRoadmapSkil;
import com.project.sanhak.domain.skil.user.UserRoadmapSkilPreque;
import com.project.sanhak.domain.user.Badge;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.lounge.repository.LoungeRepository;
import com.project.sanhak.lounge.service.LoungeService;
import com.project.sanhak.main.service.MainService;
import com.project.sanhak.mypage.dto.*;
import com.project.sanhak.mypage.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

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
    private BadgeRepository badgeRepository;
    @Autowired
    private MainService userService;
    @Autowired
    private categoryService categoryService;
    @Autowired
    private LoungeService loungeService;
    @Autowired
    private LoungeRepository loungeRepository;
    private final WebClient webClient;
    public MypageService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void masterSkill(int uid, int msId) {
        User user = userService.getUserFromUid(uid);

        int startId = (msId % 5 == 0) ? msId - 4 : msId - (msId % 5 - 1);

        for (int id = startId; id <= msId; id++) {
            MasterySkil mastery = masteryRepository.findByMSId(id);
            if (mastery == null) {
                continue;
            }

            boolean alreadyMastered = userMasterySkilRepository.existsByUMSuidAndUMSmsid(user, mastery);
            if (alreadyMastered) {
                continue;
            }

            if (id % 5 == 0) {
                CodeSkil codeSkil = mastery.getMSCSid();
                Badge badge = new Badge();
                badge.setUBCSid(codeSkil);
                badge.setUBUid(user);
                badgeRepository.save(badge);

                if (loungeRepository.findByLUid(user) != null) {
                    loungeService.increaseBnum(user);
                }
            }

            UserMasterySkil userMasterySkil = new UserMasterySkil();
            userMasterySkil.setUMSuid(user);
            userMasterySkil.setUMSmsid(mastery);
            userMasterySkilRepository.save(userMasterySkil);
        }
    }

    public quizDTO getQuiz(int msId) {
        String url = apiBaseUrl +"/createTest";
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
            else {
                throw new IllegalStateException("Python 서버 응답에 필요한 'question', 'options', 'answer' 필드가 없습니다.");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Python 서버 요청 실패: " + e.getMessage(), e);
        }
    }


    public mergeMasteryDTO getMasteryList(int uid, int csId) {
        User user = userService.getUserFromUid(uid);
        CodeSkil codeSkil = categoryService.getCodeSkilFromCSId(csId);
        List<MasterySkil> masterySkills = masteryRepository.findByMSCSid(codeSkil);
        mergeMasteryDTO masteryList = new mergeMasteryDTO();
        masteryList.setId(codeSkil.getCSId());
        masteryList.setName(codeSkil.getCSName());
        masteryList.setDescription(codeSkil.getCSDetail());
        List<masteryDTO> skillTopics = new ArrayList<>();
        for (MasterySkil masterySkill : masterySkills) {
            masteryDTO mastery = new masteryDTO();
            mastery.setId(masterySkill.getMSId());
            mastery.setTitle(masterySkill.getMSName());
            mastery.setSubtitle(Arrays.asList(masterySkill.getMSInfo1(), masterySkill.getMSInfo2(), masterySkill.getMSInfo3()));
            mastery.setStatus(checkMasteryState(user, masterySkill));
            skillTopics.add(mastery);
        }
        masteryList.setList(skillTopics);
        return masteryList;
    }

    private boolean checkMasteryState(User user, MasterySkil masterySkill) {
        return userMasterySkilRepository.existsByUMSuidAndUMSmsid(user, masterySkill);
    }

    @Transactional
    public void updateRoadmap(int urId, List<changeRoadmapDTO> requestData) {
        UserRoadmap userRoadmap = roadmapRepository.findById(urId)
                .orElseThrow(() -> new IllegalArgumentException("로드맵을 찾을 수 없습니다."));

        // 신규 노드의 csId와 저장된 URSId 매핑
        Map<Integer, Integer> csIdToURSIdMap = new HashMap<>();

        // 1. add_node 작업 처리
        for (changeRoadmapDTO dto : requestData) {
            if ("add_node".equals(dto.getActionType()) && dto.getId() == null) {
                UserRoadmapSkil newSkill = new UserRoadmapSkil();
                newSkill.setURSurid(userRoadmap);
                newSkill.setURScsName(dto.getName());
                newSkill.setURScsX(dto.getPosition().get(0));
                newSkill.setURScsY(dto.getPosition().get(1));
                newSkill.setURScsid(dto.getCsId());

                UserRoadmapSkil savedSkill = roadmapSkilRepository.save(newSkill);
                System.out.println("새로운 노드 저장: " + savedSkill.getURSId() + " (csId: " + dto.getCsId() + ")");
                csIdToURSIdMap.put(dto.getCsId(), savedSkill.getURSId());
            }
        }

        System.out.println("csIdToURSIdMap 상태: " + csIdToURSIdMap);

        // 2. add_line 및 move_node 작업 처리
        for (changeRoadmapDTO dto : requestData) {
            if ("add_line".equals(dto.getActionType())) {
                addLine(dto, urId, csIdToURSIdMap);
            } else if ("move_node".equals(dto.getActionType())) {
                if (dto.getId() == null && dto.getCsId() != null) {
                    dto.setId(csIdToURSIdMap.get(dto.getCsId()));
                }
                moveNode(dto);
            }
        }
    }
    private Integer parseIdOrName(String value, Integer urId, Map<Integer, Integer> csIdToURSIdMap) {
        try {
            // 숫자로 변환 가능하면 기존 노드의 ID
            Integer id = Integer.parseInt(value);
            System.out.println("ID로 처리됨: " + id);
            return id;
        } catch (NumberFormatException e) {
            // 숫자가 아니면 URScsName으로 검색
            System.out.println("이름으로 검색 중: " + value);
            return roadmapSkilRepository.findByURSurid_URIdAndURScsName(urId, value)
                    .map(UserRoadmapSkil::getURSId)
                    .orElseGet(() -> {
                        Integer mappedId = csIdToURSIdMap.get(value.hashCode());
                        if (mappedId == null) {
                            System.err.println("매핑할 수 없는 값: " + value + ", UR ID: " + urId);
                            throw new IllegalArgumentException("매핑할 수 없는 값: " + value + ", UR ID: " + urId);
                        }
                        System.out.println("csIdToURSIdMap에서 찾은 값: " + mappedId);
                        return mappedId;
                    });
        }
    }

    private void addLine(changeRoadmapDTO dto, Integer urId, Map<Integer, Integer> csIdToURSIdMap) {
        List<String> mapping = dto.getMapping();
        if (mapping == null || mapping.size() != 2) {
            throw new IllegalArgumentException("매핑 데이터가 올바르지 않습니다.");
        }
        System.out.println("addLine 작업 시작: " + mapping);

        // 부모와 자식 ID 변환 처리
        Integer parentId = parseIdOrName(mapping.get(0), urId, csIdToURSIdMap);
        Integer childId = parseIdOrName(mapping.get(1), urId, csIdToURSIdMap);

        if (parentId == null || childId == null) {
            System.err.println("부모 또는 자식 노드가 null입니다. parentId: " + parentId + ", childId: " + childId);
            throw new IllegalArgumentException("부모 또는 자식 노드를 찾을 수 없습니다.");
        }

        System.out.println("부모 ID: " + parentId + ", 자식 ID: " + childId);

        // 부모-자식 관계 저장
        UserRoadmapSkil parent = roadmapSkilRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("부모 노드를 찾을 수 없습니다. ID: " + parentId));
        UserRoadmapSkil child = roadmapSkilRepository.findById(childId)
                .orElseThrow(() -> new IllegalArgumentException("자식 노드를 찾을 수 없습니다. ID: " + childId));

        // 중복 관계 확인
        boolean exists = roadmapSkilPrequeRepository.existsByURSPparentscsidAndURSPchildcsid(parent.getURSId(), child.getURSId());
        if (exists) {
            System.out.println("이미 존재하는 부모-자식 관계: parentId=" + parentId + ", childId=" + childId);
            return;
        }
        // 중복이 아닌 경우에만 추가
        UserRoadmapSkilPreque preque = new UserRoadmapSkilPreque();
        preque.setURSPparentscsid(parent.getURSId());
        preque.setURSPchildcsid(child.getURSId());
        roadmapSkilPrequeRepository.save(preque);
        System.out.println("부모-자식 관계 추가됨: parentId=" + parentId + ", childId=" + childId);
    }

    private void moveNode(changeRoadmapDTO dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("노드 ID가 null입니다. 이동 작업 전에 노드가 추가되어야 합니다.");
        }
        UserRoadmapSkil skill = roadmapSkilRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("노드를 찾을 수 없습니다. ID: " + dto.getId()));
        skill.setURScsX(dto.getPosition().get(0));
        skill.setURScsY(dto.getPosition().get(1));
        roadmapSkilRepository.save(skill);
    }

    public roadmapListDTO addNewRoadmap(int uid) {
        UserRoadmap roadmap = new UserRoadmap();
        roadmap.setURuid(userService.getUserFromUid(uid));
        roadmap.setURName("새 로드맵");
        roadmap.setState(0);
        UserRoadmap newRoadmap = roadmapRepository.save(roadmap);
        if(loungeRepository.findByLUid(roadmap.getURuid())!=null){
            loungeService.increaseRnum(roadmap.getURuid());
        }
        return new roadmapListDTO(newRoadmap.getURId(),newRoadmap.getURName(),newRoadmap.getState());
    }

    public List<roadmapDTO> getRoadmaps(UserRoadmap userRoadmap) {
        List<UserRoadmapSkil> userRoadmapSkils = roadmapSkilRepository.findByURSurid(userRoadmap);

        Set<Integer> userRoadmapSkilIds = userRoadmapSkils.stream()
                .map(UserRoadmapSkil::getURSId)
                .collect(Collectors.toSet());

        return userRoadmapSkils.stream().map(userRoadmapSkil -> {
            roadmapDTO dto = new roadmapDTO();
            dto.setId(userRoadmapSkil.getURSId());
            dto.setName(userRoadmapSkil.getURScsName());
            dto.setPosition(new int[]{userRoadmapSkil.getURScsX(), userRoadmapSkil.getURScsY()});
            dto.setTag(userRoadmapSkil.getURSTag());

            List<Integer> parents = roadmapSkilPrequeRepository.findByURSPchildcsid(userRoadmapSkil.getURSId()).stream()
                    .map(UserRoadmapSkilPreque::getURSPparentscsid)
                    .filter(userRoadmapSkilIds::contains)
                    .collect(Collectors.toList());
            dto.setParent(parents);

            List<Integer> children = roadmapSkilPrequeRepository.findByURSPparentscsid(userRoadmapSkil.getURSId()).stream()
                    .map(UserRoadmapSkilPreque::getURSPchildcsid)
                    .filter(userRoadmapSkilIds::contains)
                    .collect(Collectors.toList());
            dto.setChild(children);

            return dto;
        }).collect(Collectors.toList());
    }

    public UserRoadmap getRoadmapNameByuid(int uid, int ur_id){
        User user = userService.getUserFromUid(uid);
        return roadmapRepository.findByURIdAndURuid(ur_id, user);
    }

    public List<roadmapListDTO> getRoadmapListByUid(int uid, boolean flag) {
        List<UserRoadmap> userRoadmaps;
        if (flag) {
            userRoadmaps = roadmapRepository.findByURuid_UId(uid);
        } else {
            userRoadmaps = roadmapRepository.findByURuid_UIdAndState(uid, 1);
        }

        return userRoadmaps.stream().map(userRoadmap -> {
            roadmapListDTO dto = new roadmapListDTO();
            dto.setId(userRoadmap.getURId());
            dto.setName(userRoadmap.getURName());
            dto.setState(userRoadmap.getState());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<badgeDTO> showBadge(Integer uid) {
        List <Badge> userBadges = badgeRepository.findByUBUid_UId(uid);
        return userBadges.stream().map(userBadge ->{
            badgeDTO dto = new badgeDTO();
            dto.setId(userBadge.getUBCSid().getCSId());
            dto.setName(userBadge.getUBCSid().getCSName());
            return dto;
        }).collect(Collectors.toList());
    }

    public void updateRoadmapName(int urId, String name) {
        // 1. 로드맵 조회
        UserRoadmap userRoadmap = roadmapRepository.findById(urId)
                .orElseThrow(() -> new IllegalArgumentException("로드맵을 찾을 수 없습니다."));

        // 2. 로드맵 이름 변경
        if (name != null && !name.isEmpty()) {
            userRoadmap.setURName(name);
            roadmapRepository.save(userRoadmap);
        }
    }
}