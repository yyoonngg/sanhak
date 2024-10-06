package com.project.sanhak.company.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CompanyReq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ComRId;
    private int ComRType;
    private String ComRDetail;
    private String ComRPrimium;
    private String ComRQualify;
}


