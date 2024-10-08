package com.project.sanhak.domain.skil.code;

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
    private int CSTag;
}
