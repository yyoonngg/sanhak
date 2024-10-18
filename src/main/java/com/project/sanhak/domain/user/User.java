package com.project.sanhak.domain.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UId;
    private String UGitId;
    private LocalDateTime UCreate;
    private LocalDateTime ULastLog;

}