package com.project.sanhak.skil.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Mastery_skil")
public class Mastery_skil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MS_Id;

    private String MS_Name;
    private String MS_Info;
    private int MS_Tear;
    private int MS_Tear_Level;

    @JoinColumn(name = "CS_id")
    @ManyToOne
    private CodeSkils CS_id;
}
