package com.project.sanhak.domain.skil.user;

import com.project.sanhak.domain.skil.code.CodeSkil;
import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserRoadmapSkil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UCSId;

    @ManyToOne
    @JoinColumn(name = "URId")
    private UserRoadmap URSurid;

    @ManyToOne
    @JoinColumn(name="CSID")
    private CodeSkil URScsid;
}