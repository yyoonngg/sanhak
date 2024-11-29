package com.project.sanhak.domain.skil.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserRoadmapSkilPreque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int URSPId;

    private int URSPparentscsid;

    private int URSPchildcsid;

}