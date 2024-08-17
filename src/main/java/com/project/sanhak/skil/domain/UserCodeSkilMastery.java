package com.project.sanhak.skil.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "User_Code_Skils_Masterys")
public class UserCodeSkilMastery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UCSM_Id;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User U_id;

    @ManyToOne
    @JoinColumn(name = "CSM_id")
    private CodeSkilMastery CSM_id;

}
