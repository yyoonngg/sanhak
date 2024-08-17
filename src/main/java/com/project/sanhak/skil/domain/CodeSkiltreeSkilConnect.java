package com.project.sanhak.skil.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Code_Skiltree_Skil_Connect")
@IdClass(CodeSkiltreeSkilConnectId.class)
public class CodeSkiltreeSkilConnect {

    @Id
    @ManyToOne
    @JoinColumn(name = "CST_id")
    private CodeSkiltree codeSkiltree;

    @Id
    @ManyToOne
    @JoinColumn(name = "CS_id")
    private CodeSkils codeSkils;

    private int CSSCPosition;
}
