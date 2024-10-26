package com.project.sanhak.domain.skil.code;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(PositionCodeSkilConnectId.class)
public class PositionCodeSkilConnect {

    @Id
    @ManyToOne
    @JoinColumn(referencedColumnName = "PId")
    private Positions PCSCpid;

    @Id
    @ManyToOne
    @JoinColumn(referencedColumnName = "CSId")
    private CodeSkil PCSCcsid;

    private int PCSCPosition;
}
