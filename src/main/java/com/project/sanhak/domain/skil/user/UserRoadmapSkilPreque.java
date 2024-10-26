package com.project.sanhak.domain.skil.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserRoadmapSkilPreque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int URSPId;

    @ManyToOne
    @JoinColumn(name = "URSPparentscsid", referencedColumnName = "URSid")
    private UserRoadmapSkil URSPparentscsid;

    @ManyToOne
    @JoinColumn(name = "URSPchildcsid", referencedColumnName = "URSid")
    private UserRoadmapSkil URSPchildcsid;

}