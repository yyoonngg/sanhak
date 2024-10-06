package com.project.sanhak.skil.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserMasterySkil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UMSId;

    @ManyToOne
    @JoinColumn(name = "UId")
    private User UId;

    @ManyToOne
    @JoinColumn(name = "MSId")
    private MasterySkil MSId;
}
