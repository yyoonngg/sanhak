package com.project.sanhak.category.service;

import com.project.sanhak.category.dto.categoryDTO;
import com.project.sanhak.category.repository.SkilPrequeRepository;
import com.project.sanhak.category.repository.categoryRepository;
import com.project.sanhak.domain.skil.code.CodeSkil;
import com.project.sanhak.domain.user.User;
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
        // 특정 CSCate 값을 가진 CodeSkil 엔티티만 호출
        List<CodeSkil> codeSkils = categoryRepository.findByCSCate(csCate);
        // 가져온 CodeSkil 엔티티들의 ID를 저장
        Set<Integer> codeSkilIds = codeSkils.stream()
                .map(CodeSkil::getCSId)
                .collect(Collectors.toSet());

        List<categoryDTO> categories = codeSkils.stream().map(codeSkil -> {
            categoryDTO dto = new categoryDTO();
            dto.setId(codeSkil.getCSId());
            dto.setName(codeSkil.getCSName());
            dto.setPosition(new int[]{codeSkil.getCSX(), codeSkil.getCSY()});
            dto.setTag(codeSkil.getCSTag());
            // 부모 설정
            List<Integer> parents = skilPrequeRepository.findBySPChildcsid(codeSkil).stream()
                    .map(skilPreque -> skilPreque.getSPParentscsid().getCSId())
                    .filter(codeSkilIds::contains)
                    .collect(Collectors.toList());
            dto.setParent(parents);
            // 자식 설정
            List<Integer> children = skilPrequeRepository.findBySPParentscsid(codeSkil).stream()
                    .map(skilPreque -> skilPreque.getSPChildcsid().getCSId())
                    .filter(codeSkilIds::contains)
                    .collect(Collectors.toList());
            dto.setChild(children);
            return dto;
        }).collect(Collectors.toList());
        return categories;
    }

    public CodeSkil getCodeSkilFromCSId(int CSId) {
        return categoryRepository.findById(CSId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
