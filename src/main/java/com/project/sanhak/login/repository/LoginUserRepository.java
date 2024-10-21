package com.project.sanhak.login.repository;

import com.project.sanhak.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginUserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailAndProvider(String email, String provider);
}