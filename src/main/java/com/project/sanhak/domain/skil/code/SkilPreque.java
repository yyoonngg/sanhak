package com.project.sanhak.domain.skil.code;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SkilPreque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int SPId;

    private int SPParentscsid;
    private int SPChildcsid;
}


