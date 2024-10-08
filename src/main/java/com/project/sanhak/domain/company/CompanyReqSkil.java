package com.project.sanhak.domain.company;

import com.project.sanhak.domain.skil.code.CodeSkil;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CompanyReqSkil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ComRSId;

    @JoinColumn(referencedColumnName = "CSId")
    @ManyToOne
    private CodeSkil ComRScsid;

    @JoinColumn(referencedColumnName = "ComRId")
    @ManyToOne
    private CompanyReq ComRScomrid;
}

