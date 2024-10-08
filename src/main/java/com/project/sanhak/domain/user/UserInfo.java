package com.project.sanhak.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class UserInfo {

    @Id
    @OneToOne
    @JoinColumn(name = "UId")
    private User UIuid;

    private String UIBio;
    private String UIProfileImg;
    private int UIExp;
    private String UINowPosition;
    private String UIDesirePosition;
}
