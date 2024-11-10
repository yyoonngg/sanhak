package com.project.sanhak.lounge.repository;

import com.project.sanhak.domain.lounge.LoungeView;
import com.project.sanhak.domain.lounge.Lounges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoungeViewRepository extends JpaRepository<LoungeView, Integer> {
    LoungeView findByLVlid(Lounges lounge);
}