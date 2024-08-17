package com.project.sanhak.skil.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Code_Skil_Masterys")
public class CodeSkilMastery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CSMId;

    private String CSMName;
    private String CSMInfo;
    private int CSMTear;
    private int CSMTearLevel;

    @ManyToOne
    @JoinColumn(name = "CS_id")
    private CodeSkils codeSkils;
}
