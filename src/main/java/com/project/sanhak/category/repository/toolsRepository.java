package com.project.sanhak.category.repository;

import com.project.sanhak.domain.skil.code.Tools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface toolsRepository extends JpaRepository<Tools, Integer> {
    List<Tools> findByNameIn(List<String> toolsNames);
}

