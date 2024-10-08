package com.project.sanhak.domain.skil.user;

import com.project.sanhak.domain.skil.code.CodeSkil;
import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserRoadmap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int URId;

    @ManyToOne
    @JoinColumn(name = "UId")
    private User URuid;
}