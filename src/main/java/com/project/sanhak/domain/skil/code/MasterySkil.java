package com.project.sanhak.domain.skil.code;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MasterySkil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MSId;
    private String MSName;
    private String MSInfo1;
    private String MSInfo2;
    private String MSInfo3;

    @JoinColumn(referencedColumnName = "CSId")
    @ManyToOne
    private CodeSkil MS_csid;
}
