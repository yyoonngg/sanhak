package com.project.sanhak.category.service;

import com.project.sanhak.category.dto.categoryDTO;
import com.project.sanhak.category.repository.SkilPrequeRepository;
import com.project.sanhak.category.repository.categoryRepository;
import com.project.sanhak.domain.skil.code.CodeSkil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class categoryService {

    @Autowired
    private categoryRepository categoryRepository;
    @Autowired
    private SkilPrequeRepository skilPrequeRepository;

    public List<categoryDTO> getSkilNode(String csCate) {
        // 1. 특정 카테고리에 매칭된 CodeSkil만 가져오기
        List<CodeSkil> codeSkils = categoryRepository.findAll().stream()
                .filter(codeSkil -> isCategoryMatched(codeSkil, csCate))
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
            dto.setTag(codeSkil.getCSTag());

            // 부모 설정
            List<Integer> parents = skilPrequeRepository.findBySPChildcsid(codeSkil).stream()
                    .map(preque -> preque.getSPParentscsid().getCSId())
                    .filter(codeSkilIds::contains)
                    .collect(Collectors.toList());
            dto.setParent(parents);

            // 자식 설정
            List<Integer> children = skilPrequeRepository.findBySPParentscsid(codeSkil).stream()
                    .map(preque -> preque.getSPChildcsid().getCSId())
                    .filter(codeSkilIds::contains)
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

    //특정 스킬 ID로 CodeSkil 엔티티 가져오기
    public CodeSkil getCodeSkilFromCSId(int CSId) {
        return categoryRepository.findById(CSId)
                .orElseThrow(() -> new IllegalArgumentException("CodeSkil을 찾을 수 없습니다."));
    }
}
