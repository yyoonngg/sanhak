package com.project.sanhak.category.repository;

import com.project.sanhak.domain.skil.code.CodeSkil;
import com.project.sanhak.domain.skil.code.SkilPreque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkilPrequeRepository extends JpaRepository<SkilPreque, Integer> {
    List<SkilPreque> findBySPParentscsid(CodeSkil parent);

    List<SkilPreque> findBySPChildcsid(CodeSkil child);
}
