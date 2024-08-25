package com.project.sanhak.skil.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Position_Code_Skil_Connect")
@IdClass(PositionCodeSkilConnectId.class)
public class PositionCodeSkilConnect {

    @Id
    @ManyToOne
    @JoinColumn(name = "P_id")
    private Positions P_id;

    @Id
    @ManyToOne
    @JoinColumn(name = "CS_id")
    private CodeSkils CS_id;

    private int PCSC_Position;
}
