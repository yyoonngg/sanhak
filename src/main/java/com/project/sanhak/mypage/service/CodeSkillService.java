package com.project.sanhak.mypage.service;

import com.project.sanhak.domain.skil.code.CodeSkil;
import com.project.sanhak.mypage.repository.CodeSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeSkillService {

    @Autowired
    private CodeSkillRepository codeSkillRepository;

    public CodeSkil findByName(String CSName) {
        return codeSkillRepository.findByCSName(CSName)
                .orElseThrow(() -> new IllegalArgumentException("해당 스킬을 찾을 수 없습니다. 스킬 이름: " + CSName));
    }


    // 추가적인 메서드
}