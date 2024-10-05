package com.project.sanhak.company.domain;

import com.project.sanhak.skil.domain.CodeSkil;
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
    private CodeSkil CSId;

    @JoinColumn(name = "ComRId")
    @ManyToOne
    private CompanyReq ComRId;
}

