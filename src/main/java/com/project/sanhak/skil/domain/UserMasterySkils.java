package com.project.sanhak.skil.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "User_Masterys_Skils")
public class UserMasterySkils {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UMS_Id;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User U_id;

    @ManyToOne
    @JoinColumn(name = "MS_id")
    private Mastery_skil MS_id;
}
