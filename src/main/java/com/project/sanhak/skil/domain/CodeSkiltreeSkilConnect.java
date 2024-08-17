package com.project.sanhak.skil.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Code_Skiltree_Skil_Connect")
@IdClass(CodeSkiltreeSkilConnectId.class)
public class CodeSkiltreeSkilConnect {

    @Id
    @ManyToOne
    @JoinColumn(name = "CST_id")
    private CodeSkiltree CST_id;

    @Id
    @ManyToOne
    @JoinColumn(name = "CS_id")
    private CodeSkils CS_id;

    private int CSSC_Position;
}
