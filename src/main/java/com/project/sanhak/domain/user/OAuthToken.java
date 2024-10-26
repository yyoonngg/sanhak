package com.project.sanhak.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity

public class OAuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OAuthUid;
    private String username;
    private String provider;
    private String email;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String expireDate;


    public OAuthToken updateUser(String username, String email, String provider, String accessToken,
                                 String refreshToken, String expireDate) {
        this.username = username;
        this.provider = provider;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireDate = expireDate;
        return this;
    }
}
