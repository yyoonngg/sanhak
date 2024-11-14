package com.project.sanhak.domain.company;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ComComReqConnect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CCRCId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "COMId")
    private Company CCRCcomid;

    @ManyToOne
    @JoinColumn(referencedColumnName = "ComRId")
    private CompanyReq CCRCcomrid;

}
