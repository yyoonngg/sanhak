package com.project.sanhak.domain.skil.code;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class cateFlag {

    @Embeddable
    @Data
    public static class Category {
        private boolean active;
        private int x;
        private int y;
    }
}