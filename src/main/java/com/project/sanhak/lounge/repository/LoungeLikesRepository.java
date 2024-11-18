package com.project.sanhak.lounge.repository;

import com.project.sanhak.domain.lounge.LoungeLikes;
import com.project.sanhak.domain.lounge.Lounges;
import com.project.sanhak.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoungeLikesRepository extends JpaRepository<LoungeLikes, Integer> {
    LoungeLikes findByLLlidAndLLuid(Lounges lounge, User user);
}