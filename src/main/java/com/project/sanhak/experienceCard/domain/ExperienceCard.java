package com.project.sanhak.experienceCard.domain;

import com.project.sanhak.board.domain.Boards;
import com.project.sanhak.user.domain.User;
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
    private User UId;
    private String ECPosition;
    private String ECLang;
    private String ECStack;
    private String ECEct;
    private String ECPdf;
    private String ECSummary;
}

