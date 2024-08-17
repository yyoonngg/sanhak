package com.project.sanhak.user.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "User_info")
public class UserInfo {

    @Id
    @OneToOne
    @JoinColumn(name = "U_id")
    private User U_id;

    private String UI_Bio;
    private String UI_ProfilrImg;
    private int UI_Exp;
    private String UI_NowPosition;
    private String UI_DesirePosition;
}
