package com.project.sanhak.domain.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UIid;

    @OneToOne
    @JoinColumn(referencedColumnName = "UId")
    private User UIuid;
    private String UIUserName;
    private String UIUserEmail;
    private String UIBio;
    private String UIProfileImg;
    private String UIDesirePosition;
}
