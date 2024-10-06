package com.project.sanhak.user.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserInfo {

    @Id
    @OneToOne
    @JoinColumn(name = "UId")
    private User UId;

    private String UIBio;
    private String UIProfileImg;
    private int UIExp;
    private String UINowPosition;
    private String UIDesirePosition;
}
