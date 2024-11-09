// ProfileRepository.java
package com.project.sanhak.main.repository;

import com.project.sanhak.domain.user.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByPRuid_UId(int uid);  // 'UId'로 수정
}
