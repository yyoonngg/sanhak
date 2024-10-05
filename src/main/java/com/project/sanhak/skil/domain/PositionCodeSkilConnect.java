package com.project.sanhak.skil.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(PositionCodeSkilConnectId.class)
public class PositionCodeSkilConnect {

    @Id
    @ManyToOne
    @JoinColumn(name = "PId")
    private Positions PId;

    @Id
    @ManyToOne
    @JoinColumn(name = "CSId")
    private CodeSkil CSId;

    private int PCSCPosition;
}
