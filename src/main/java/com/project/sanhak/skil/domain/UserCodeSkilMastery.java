package com.project.sanhak.skil.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;

@Entity
@Table(name = "User_Code_Skils_Masterys")
public class UserCodeSkilMastery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UCSMId;

    private String field;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CSM_id")
    private CodeSkilMastery codeSkilMastery;

}
