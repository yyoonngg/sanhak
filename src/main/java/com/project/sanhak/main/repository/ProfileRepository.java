// ProfileRepository.java
package com.project.sanhak.main.repository;

import com.project.sanhak.domain.user.Profile;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.domain.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByUIuid(User user);
}
