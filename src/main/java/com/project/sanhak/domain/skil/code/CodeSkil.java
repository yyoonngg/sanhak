package com.project.sanhak.domain.skil.code;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CodeSkil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CSId;

    private String CSName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "active", column = @Column(name = "frontend_active")),
            @AttributeOverride(name = "x", column = @Column(name = "frontend_x")),
            @AttributeOverride(name = "y", column = @Column(name = "frontend_y"))
    })
    private cateFlag.Category frontend;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "active", column = @Column(name = "backend_active")),
            @AttributeOverride(name = "x", column = @Column(name = "backend_x")),
            @AttributeOverride(name = "y", column = @Column(name = "backend_y"))
    })
    private cateFlag.Category backend;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "active", column = @Column(name = "data_active")),
            @AttributeOverride(name = "x", column = @Column(name = "data_x")),
            @AttributeOverride(name = "y", column = @Column(name = "data_y"))
    })
    private cateFlag.Category data;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "active", column = @Column(name = "security_active")),
            @AttributeOverride(name = "x", column = @Column(name = "security_x")),
            @AttributeOverride(name = "y", column = @Column(name = "security_y"))
    })
    private cateFlag.Category security;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "active", column = @Column(name = "application_active")),
            @AttributeOverride(name = "x", column = @Column(name = "application_x")),
            @AttributeOverride(name = "y", column = @Column(name = "application_y"))
    })
    private cateFlag.Category application;

    private String CSTag;
    private String CSDetail;
}
