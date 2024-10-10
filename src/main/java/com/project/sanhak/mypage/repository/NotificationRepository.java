package com.project.sanhak.mypage.repository;

import com.project.sanhak.domain.user.Notifications;
import com.project.sanhak.domain.user.OAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, Integer> {
}
