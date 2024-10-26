package com.project.sanhak.domain.card;

import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    private String ECSkill;
    private String ECTool;
    private String ECReflection;
    private String ECSummary;
    private String ECImageUrl;
    private String ECPdfName;
    private String ECPdfUrl;
    @Lob
    private String ECLink;
}