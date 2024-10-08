package com.project.sanhak.domain.company;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "Com_Com_Req_Connect")
public class ComComReqConnect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CCRCId;

    @ManyToOne
    @JoinColumn(name = "COMId")
    private Company CCRCcomid;

    @ManyToOne
    @JoinColumn(name = "ComRId")
    private CompanyReq CCRCcomrid;

}
