package com.project.sanhak.domain.skil.code;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class CodeSkil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CSId;
    private String CSName;
    @Embedded
    private cateFlag csCate;
    private int CSX;
    private int CSY;
    private String CSTag;
    private String CSDetail;
}
