package com.project.sanhak.skil.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Code_Skiltree")
public class CodeSkiltree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CST_Id;

    private String CST_Name;
    private String CST_Info;
}
