package com.project.sanhak.domain.company;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

