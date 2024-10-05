package com.project.sanhak.user.domain;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "OAuthToken")
public class OAuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OAuthUid;
    private String acessToken;
    private String refreshToken;
    private String tokenType;
    private LocalDateTime expireDate;
}