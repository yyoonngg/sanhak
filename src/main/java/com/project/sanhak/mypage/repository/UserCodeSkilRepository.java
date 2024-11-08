package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.skil.user.UserCodeSkil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCodeSkilRepository extends JpaRepository<UserCodeSkil, Integer> {
}
