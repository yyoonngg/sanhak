package com.project.sanhak.skil.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "User_Code_Skils")
public class UserCodeSkils {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UCS_Id;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User U_id;

    @ManyToOne
    @JoinColumn(name = "CS_id")
    private CodeSkils CS_id;
}
