package com.project.sanhak.skil.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MasterySkil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MSId;
    private String MSName;
    private String MSInfo;

    @JoinColumn(name = "CSId")
    @ManyToOne
    private CodeSkil CSId;
}
