package com.project.sanhak.domain.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UIid;

    @OneToOne
    @JoinColumn(referencedColumnName = "UId")
    private User UIuid;

    private String UIBio;
    private String UIProfileImg;
    private int UIExp;
    private String UINowPosition;
    private String UIDesirePosition;
}
