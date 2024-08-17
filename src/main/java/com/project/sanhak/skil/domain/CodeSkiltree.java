package com.project.sanhak.skil.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Code_Skiltree")
public class CodeSkiltree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CSTId;

    private String CSTName;
    private String CSTInfo;
}
