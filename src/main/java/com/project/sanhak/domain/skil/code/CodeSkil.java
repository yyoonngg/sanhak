package com.project.sanhak.domain.skil.code;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CodeSkil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CSId;
    private String CSName;
    private String CSCate;
    private int CSX;
    private int CSY;
    private String CSTag;
    private String CSDetail;
}
