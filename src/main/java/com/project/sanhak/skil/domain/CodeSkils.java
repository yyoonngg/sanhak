package com.project.sanhak.skil.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Code_Skils")
public class CodeSkils {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CS_Id;

    private String CS_Name;
    private String CS_Info;
    private int CS_Cate;
    private int CS_Prereq;
}
