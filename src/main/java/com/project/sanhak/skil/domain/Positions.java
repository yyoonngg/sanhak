package com.project.sanhak.skil.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Positions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PId;

    private String PName;
    private String PInfo;
}
