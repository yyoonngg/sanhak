// CodeSkillRepository.java
package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.skil.code.CodeSkil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeSkillRepository extends JpaRepository<CodeSkil, Integer> {
    Optional<CodeSkil> findByCSName(String CSName); // 스킬 이름으로 CodeSkil 조회
}
