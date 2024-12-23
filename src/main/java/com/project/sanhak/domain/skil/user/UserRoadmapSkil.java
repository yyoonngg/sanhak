package com.project.sanhak.domain.skil.user;

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
    private String URSTag;
    private String URScsName;
    private int URScsX;
    private int URScsY;
    private int URScsid;
}