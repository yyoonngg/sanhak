package com.project.sanhak.domain.user;

import com.project.sanhak.domain.skil.code.CodeSkil;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UBId;
    @ManyToOne
    @JoinColumn(referencedColumnName = "CSId")
    private CodeSkil UBCSid;
    @ManyToOne
    @JoinColumn(referencedColumnName = "UId")
    private User UBUid;
}
