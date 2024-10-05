package com.project.sanhak.company.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int COMId;
    private String COMName;
    private String COMMoney;
    private String COMWelfare;
    private String COMPlace;
    private float COMLatitude;
    private float COMLongitude;
}

