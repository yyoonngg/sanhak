package com.project.sanhak.domain.user;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UId;
    private String UEmailId;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime UCreate;
    private LocalDateTime ULastLog;
}