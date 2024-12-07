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
            @AttributeOverride(name = "y", column = @Column(name = "frontend_y")),
            @AttributeOverride(name = "tag", column = @Column(name = "frontend_tag"))
    })
    private cateFlag.Category frontend;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "active", column = @Column(name = "backend_active")),
            @AttributeOverride(name = "x", column = @Column(name = "backend_x")),
            @AttributeOverride(name = "y", column = @Column(name = "backend_y")),
            @AttributeOverride(name = "tag", column = @Column(name = "backend_tag"))
    })
    private cateFlag.Category backend;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "active", column = @Column(name = "data_active")),
            @AttributeOverride(name = "x", column = @Column(name = "data_x")),
            @AttributeOverride(name = "y", column = @Column(name = "data_y")),
            @AttributeOverride(name = "tag", column = @Column(name = "data_tag"))
    })
    private cateFlag.Category data;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "active", column = @Column(name = "security_active")),
            @AttributeOverride(name = "x", column = @Column(name = "security_x")),
            @AttributeOverride(name = "y", column = @Column(name = "security_y")),
            @AttributeOverride(name = "tag", column = @Column(name = "security_tag"))
    })
    private cateFlag.Category security;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "active", column = @Column(name = "application_active")),
            @AttributeOverride(name = "x", column = @Column(name = "application_x")),
            @AttributeOverride(name = "y", column = @Column(name = "application_y")),
            @AttributeOverride(name = "tag", column = @Column(name = "application_tag"))
    })
    private cateFlag.Category application;

    private String CSDetail;
}
