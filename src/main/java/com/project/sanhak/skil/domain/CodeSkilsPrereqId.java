package com.project.sanhak.skil.domain;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Data
public class CodeSkilsPrereqId implements Serializable {
    private int CS_id;
    private int CSP_id;
}
