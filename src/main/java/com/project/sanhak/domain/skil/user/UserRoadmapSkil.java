package com.project.sanhak.domain.skil.user;

import com.project.sanhak.domain.skil.code.CodeSkil;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserRoadmapSkil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int URSId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "URId")
    private UserRoadmap URSurid;
    private int URScsid;
    private String URScsName;
    private int URScsX;
    private int URScsY;
}