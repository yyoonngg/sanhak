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
        private Integer x;
        private Integer y;
        private String tag;
    }
}