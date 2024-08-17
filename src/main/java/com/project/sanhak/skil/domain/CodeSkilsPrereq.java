package com.project.sanhak.skil.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Code_Skils_Prereq")
@IdClass(CodeSkilsPrereqId.class)
public class CodeSkilsPrereq {

    @Id
    @ManyToOne
    @JoinColumn(name = "CS_id")
    private CodeSkils codeSkil;

    @Id
    @ManyToOne
    @JoinColumn(name = "CSP_id")
    private CodeSkils prereqCodeSkil;

    private int CSPTear;
}

