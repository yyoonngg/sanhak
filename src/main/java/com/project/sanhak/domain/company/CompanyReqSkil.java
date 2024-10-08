package com.project.sanhak.domain.company;

import com.project.sanhak.domain.skil.code.CodeSkil;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Company_Req_Skil")
public class CompanyReqSkil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ComRSId;

    @JoinColumn(name = "CSId")
    @ManyToOne
    private CodeSkil ComRScsid;

    @JoinColumn(name = "ComRId")
    @ManyToOne
    private CompanyReq ComRScomrid;
}

