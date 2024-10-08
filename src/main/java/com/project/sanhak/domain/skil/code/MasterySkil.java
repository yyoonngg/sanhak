package com.project.sanhak.domain.skil.code;

import com.project.sanhak.domain.skil.code.CodeSkil;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MasterySkil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MSId;
    private String MSName;
    private String MSInfo;

    @JoinColumn(name = "CSId")
    @ManyToOne
    private CodeSkil MS_csid;
}
