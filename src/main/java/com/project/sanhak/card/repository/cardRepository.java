package com.project.sanhak.card.repository;

import com.project.sanhak.domain.card.ExperienceCard;
import com.project.sanhak.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface cardRepository extends JpaRepository<ExperienceCard, Integer> {
    List<ExperienceCard> findByECuid_UId(int UId);

    Optional<ExperienceCard> findByECId(int ecId);

    Optional<ExperienceCard> findByECIdAndECuid(int id, User user);

    int countByECuid(User user);
}
