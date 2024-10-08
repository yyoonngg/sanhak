package com.project.sanhak.domain.card;

import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ExperienceCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ECId;

    @JoinColumn(name = "UId")
    @ManyToOne
    private User ECuid;

    private String ECTitle;
    private String ECPosition;
    private String ECTool;
    private String ECReflection;
    private String ECPdf;
    private String ECSummary;
}

