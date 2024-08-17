package com.project.sanhak.skil.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Code_Skil_Masterys")
public class CodeSkilMastery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CSM_Id;

    private String CSM_Name;
    private String CSM_Info;
    private int CSM_Tear;
    private int CSM_Tear_Level;

    @JoinColumn(name = "CS_id")
    @ManyToOne
    private CodeSkils CS_id;
}
