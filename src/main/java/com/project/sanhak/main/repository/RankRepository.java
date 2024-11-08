package com.project.sanhak.main.repository;

import com.project.sanhak.domain.user.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<Rank, Integer> {
}
