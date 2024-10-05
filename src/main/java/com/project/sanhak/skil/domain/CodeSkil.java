package com.project.sanhak.skil.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CodeSkil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CSId;
    private String CSName;
    private int CSCate;
    private int CSX;
    private int CSY;
}
