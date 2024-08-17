package com.project.sanhak.skil.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Code_Skils_Prereq")
@IdClass(CodeSkilsPrereqId.class)
public class CodeSkilsPrereq {

    @Id
    @ManyToOne
    @JoinColumn(name = "CS_id")
    private CodeSkils CS_id;

    @Id
    @ManyToOne
    @JoinColumn(name = "CSP_id")
    private CodeSkils CSP_id;

    private int CSP_Tear;
}

