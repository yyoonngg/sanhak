package com.project.sanhak.domain.card;

import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Entity
public class ExperienceCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ECId;

    @JoinColumn(referencedColumnName = "UId")
    @ManyToOne
    private User ECuid;
    private LocalDateTime ECFromDate;
    private LocalDateTime ECToDate;
    private String ECTitle;
    private String ECPosition;
    private String ECSkil;
    private String ECTool;
    private String ECReflection;
    private String ECPdf;
    private String ECSummary;
    private String ECImageUrl;
    private String ECPdfUrl;
    private String ECLink;
}