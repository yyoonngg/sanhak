package com.project.sanhak.user.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "User_info")
public class UserInfo {

    @Id
    @OneToOne
    @JoinColumn(name = "U_id")
    private User user;

    private String UIBio;
    private String UIProfilrImg;
    private int UIExp;
    private String UINowPosition;
    private String UIDesirePosition;
    private String field;

}
