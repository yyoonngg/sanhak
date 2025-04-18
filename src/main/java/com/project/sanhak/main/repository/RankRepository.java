package com.project.sanhak.main.repository;

import com.project.sanhak.domain.user.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<Ranking, Integer> {
}
