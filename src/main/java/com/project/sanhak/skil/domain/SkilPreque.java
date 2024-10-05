package com.project.sanhak.skil.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SkilPreque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int SP_id;

    private int SP_parents_id;
    private int SP_child_id;
}


