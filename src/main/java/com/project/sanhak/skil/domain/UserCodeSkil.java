package com.project.sanhak.skil.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserCodeSkil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UCSId;

    @ManyToOne
    @JoinColumn(name = "UId")
    private User UId;

    @ManyToOne
    @JoinColumn(name = "CSId")
    private CodeSkil CSId;
}
