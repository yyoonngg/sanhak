package com.project.sanhak.domain.skil.code;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class cateFlag {
    private boolean frontend;
    private boolean backend;
    private boolean data;
    private boolean security;
    private boolean application;
}
