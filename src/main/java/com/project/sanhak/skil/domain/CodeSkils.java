package com.project.sanhak.skil.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Code_Skils")
public class CodeSkils {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CSId;

    private String CSName;
    private String CSInfo;
    private int CSCate;
    private int CSPrereq;
}
