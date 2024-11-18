package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.skil.code.MasterySkil;
import com.project.sanhak.domain.skil.user.UserMasterySkil;
import com.project.sanhak.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMasterySkilRepository extends JpaRepository<UserMasterySkil, Integer> {
    boolean findByUMSuidAndUMSmsid(User uid, MasterySkil masterySkil);

    boolean existsByUMSuidAndUMSmsid(User user, MasterySkil mastery);
}
