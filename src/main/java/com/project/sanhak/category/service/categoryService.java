package com.project.sanhak.category.service;

import com.project.sanhak.category.dto.categoryDTO;
import com.project.sanhak.category.dto.categorySkillDTO;
import com.project.sanhak.category.dto.skillDTO;
import com.project.sanhak.category.dto.toolDTO;
import com.project.sanhak.category.repository.SkilPrequeRepository;
import com.project.sanhak.category.repository.categoryRepository;
import com.project.sanhak.category.repository.toolsRepository;
import com.project.sanhak.domain.skil.code.CodeSkil;
import com.project.sanhak.domain.skil.code.SkilPrequeCateFlags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class categoryService {

    @Autowired
    private categoryRepository categoryRepository;
    @Autowired
    private SkilPrequeRepository skilPrequeRepository;
    @Autowired
    private toolsRepository toolsRepository;

    public List<categoryDTO> getSkilNode(String csCate) {
        // 1. 특정 카테고리에 매칭된 CodeSkil만 가져오기
        List<CodeSkil> codeSkils = categoryRepository.findAll().stream()
                .filter(codeSkil -> isCategory(codeSkil, csCate))
                .toList();

        // 2. 가져온 CodeSkil 엔티티들의 ID를 저장
        Set<Integer> codeSkilIds = codeSkils.stream()
                .map(CodeSkil::getCSId)
                .collect(Collectors.toSet());

        // 3. CodeSkil -> categoryDTO로 변환
        return codeSkils.stream().map(codeSkil -> {
            categoryDTO dto = new categoryDTO();
            dto.setId(codeSkil.getCSId());
            dto.setName(codeSkil.getCSName());

            // 카테고리별 좌표 설정
            dto.setPosition(getCategoryPosition(codeSkil, csCate));
            dto.setTag(getCategoryTag(codeSkil,csCate));

            // 부모 설정
            List<Integer> parents = skilPrequeRepository.findBySPChildcsid(codeSkil).stream()
                    .filter(preque -> isCategoryMatched(preque.getSpCateFlags(), csCate)) // 카테고리에 맞는지 확인
                    .map(preque -> preque.getSPParentscsid().getCSId())
                    .filter(codeSkilIds::contains) // 같은 csname에 해당하는 ID만 포함
                    .collect(Collectors.toList());
            dto.setParent(parents);

            // 자식 설정
            List<Integer> children = skilPrequeRepository.findBySPParentscsid(codeSkil).stream()
                    .filter(preque -> isCategoryMatched(preque.getSpCateFlags(), csCate)) // 카테고리에 맞는지 확인
                    .map(preque -> preque.getSPChildcsid().getCSId())
                    .filter(codeSkilIds::contains) // 같은 csname에 해당하는 ID만 포함
                    .collect(Collectors.toList());
            dto.setChild(children);

            return dto;
        }).toList();
    }

    //카테고리에 따른 활성 여부 확인

    private boolean isCategoryMatched(CodeSkil codeSkil, String csCate) {
        if (codeSkil == null || csCate == null) {
            return false;
        }

        return switch (csCate.toLowerCase()) {
            case "frontend" -> codeSkil.getFrontend() != null && codeSkil.getFrontend().isActive();
            case "backend" -> codeSkil.getBackend() != null && codeSkil.getBackend().isActive();
            case "data" -> codeSkil.getData() != null && codeSkil.getData().isActive();
            case "security" -> codeSkil.getSecurity() != null && codeSkil.getSecurity().isActive();
            case "application" -> codeSkil.getApplication() != null && codeSkil.getApplication().isActive();
            default -> false;
        };
    }

    //카테고리에 따른 좌표 가져오기

    private int[] getCategoryPosition(CodeSkil codeSkil, String csCate) {
        if (codeSkil == null || csCate == null) {
            return new int[]{0, 0};
        }

        // 카테고리에 따라 위치 반환
        return switch (csCate.toLowerCase()) {
            case "frontend" -> codeSkil.getFrontend() != null
                    ? new int[]{codeSkil.getFrontend().getX(), codeSkil.getFrontend().getY()}
                    : new int[]{0, 0};
            case "backend" -> codeSkil.getBackend() != null
                    ? new int[]{codeSkil.getBackend().getX(), codeSkil.getBackend().getY()}
                    : new int[]{0, 0};
            case "data" -> codeSkil.getData() != null
                    ? new int[]{codeSkil.getData().getX(), codeSkil.getData().getY()}
                    : new int[]{0, 0};
            case "security" -> codeSkil.getSecurity() != null
                    ? new int[]{codeSkil.getSecurity().getX(), codeSkil.getSecurity().getY()}
                    : new int[]{0, 0};
            case "application" -> codeSkil.getApplication() != null
                    ? new int[]{codeSkil.getApplication().getX(), codeSkil.getApplication().getY()}
                    : new int[]{0, 0};
            default -> new int[]{0, 0}; // 기본값 반환
        };
    }

    private String getCategoryTag(CodeSkil codeSkil, String csCate) {
        if (codeSkil == null || csCate == null) {
            return "none";
        }

        // 카테고리에 따라 위치 반환
        return switch (csCate.toLowerCase()) {
            case "frontend" -> codeSkil.getFrontend() != null
                    ? codeSkil.getFrontend().getTag()
                    : "none";
            case "backend" -> codeSkil.getBackend() != null
                    ? codeSkil.getBackend().getTag()
                    : "none";
            case "data" -> codeSkil.getData() != null
                    ? codeSkil.getData().getTag()
                    : "none";
            case "security" -> codeSkil.getSecurity() != null
                    ? codeSkil.getSecurity().getTag()
                    : "none";
            case "application" -> codeSkil.getApplication() != null
                    ? codeSkil.getApplication().getTag()
                    : "none";
            default -> "none"; // 기본값 반환
        };
    }


    private boolean isCategoryMatched(SkilPrequeCateFlags flags, String csCate) {
        if (flags == null || csCate == null) {
            return false;
        }

        return switch (csCate.toLowerCase()) {
            case "frontend" -> flags.isFrontend();
            case "backend" -> flags.isBackend();
            case "data" -> flags.isData();
            case "security" -> flags.isSecurity();
            case "application" -> flags.isApplication();
            default -> false;
        };
    }

    private boolean isCategory(CodeSkil codeSkil, String csCate) {
        if (codeSkil == null || csCate == null) {
            return false;
        }

        return switch (csCate.toLowerCase()) {
            case "frontend" -> codeSkil.getFrontend() != null && codeSkil.getFrontend().isActive();
            case "backend" -> codeSkil.getBackend() != null && codeSkil.getBackend().isActive();
            case "data" -> codeSkil.getData() != null && codeSkil.getData().isActive();
            case "security" -> codeSkil.getSecurity() != null && codeSkil.getSecurity().isActive();
            case "application" -> codeSkil.getApplication() != null && codeSkil.getApplication().isActive();
            default -> false;
        };
    }


    //특정 스킬 ID로 CodeSkil 엔티티 가져오기
    public CodeSkil getCodeSkilFromCSId(int CSId) {
        return categoryRepository.findById(CSId)
                .orElseThrow(() -> new IllegalArgumentException("CodeSkil을 찾을 수 없습니다."));
    }


    public List<categorySkillDTO> getAllSkillsByCategory() {
        List<CodeSkil> codeSkils = categoryRepository.findAll();

        List<skillDTO> frontendSkills = new ArrayList<>();
        List<skillDTO> backendSkills = new ArrayList<>();
        List<skillDTO> dataSkills = new ArrayList<>();
        List<skillDTO> securitySkills = new ArrayList<>();
        List<skillDTO> applicationSkills = new ArrayList<>();

        for (CodeSkil codeSkil : codeSkils) {
            if (codeSkil.getFrontend() != null && codeSkil.getFrontend().isActive()) {
                frontendSkills.add(new skillDTO(codeSkil.getCSId(), codeSkil.getCSName()));
            }
            if (codeSkil.getBackend() != null && codeSkil.getBackend().isActive()) {
                backendSkills.add(new skillDTO(codeSkil.getCSId(), codeSkil.getCSName()));
            }
            if (codeSkil.getData() != null && codeSkil.getData().isActive()) {
                dataSkills.add(new skillDTO(codeSkil.getCSId(), codeSkil.getCSName()));
            }
            if (codeSkil.getSecurity() != null && codeSkil.getSecurity().isActive()) {
                securitySkills.add(new skillDTO(codeSkil.getCSId(), codeSkil.getCSName()));
            }
            if (codeSkil.getApplication() != null && codeSkil.getApplication().isActive()) {
                applicationSkills.add(new skillDTO(codeSkil.getCSId(), codeSkil.getCSName()));
            }
        }

        List<categorySkillDTO> categories = new ArrayList<>();
        categories.add(new categorySkillDTO("frontend", frontendSkills));
        categories.add(new categorySkillDTO("backend", backendSkills));
        categories.add(new categorySkillDTO("data", dataSkills));
        categories.add(new categorySkillDTO("security", securitySkills));
        categories.add(new categorySkillDTO("application", applicationSkills));

        return categories;
    }


    public List<toolDTO> getAllTools() {
        return toolsRepository.findAll().stream()
                .map(tool -> new toolDTO(tool.getId(), tool.getName()))
                .collect(Collectors.toList());
    }
}
