package com.project.sanhak.category.repository;

import com.project.sanhak.domain.skil.code.CodeSkil;
import com.project.sanhak.domain.skil.code.MasterySkil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface masteryRepository extends JpaRepository<MasterySkil, Integer> {
    List<MasterySkil> findByMS_csid(int csId);

    MasterySkil findByMSId(int msId);

}