package com.project.sanhak.skil.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;

@Entity
@Table(name = "User_Code_Skils")
public class UserCodeSkils {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UCSId;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CS_id")
    private CodeSkils codeSkils;
}
