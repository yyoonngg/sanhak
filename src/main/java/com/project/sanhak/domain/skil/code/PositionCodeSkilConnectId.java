package com.project.sanhak.domain.skil.code;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class PositionCodeSkilConnectId implements Serializable {
    private int PCSCpid;
    private int PCSCcsid;
}
