package com.project.sanhak.domain.skil.user;

import com.project.sanhak.domain.skil.code.CodeSkil;
import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserCodeSkil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UCSId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "UId")
    private User UCSuid;

    @ManyToOne
    @JoinColumn(referencedColumnName = "CSId")
    private CodeSkil UCScsid;
}
