package com.project.sanhak.domain.skil.user;

import com.project.sanhak.domain.skil.code.MasterySkil;
import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserMasterySkil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UMSId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "UId")
    private User UMSuid;

    @ManyToOne
    @JoinColumn(referencedColumnName = "MSId")
    private MasterySkil UMSmsid;
}
