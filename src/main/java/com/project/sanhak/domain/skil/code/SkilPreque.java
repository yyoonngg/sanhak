package com.project.sanhak.domain.skil.code;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SkilPreque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int SPId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "CSId")
    private CodeSkil SPParentscsid;

    @ManyToOne
    @JoinColumn(referencedColumnName = "CSId")
    private CodeSkil SPChildcsid;
}


