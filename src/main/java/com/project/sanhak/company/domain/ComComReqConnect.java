package com.project.sanhak.company.domain;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "Com_Com_Req_Connect")
public class ComComReqConnect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "COMId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "ComRId")
    private CompanyReq companyReq;

}
