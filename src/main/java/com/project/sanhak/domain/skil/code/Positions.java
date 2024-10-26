package com.project.sanhak.domain.skil.code;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
