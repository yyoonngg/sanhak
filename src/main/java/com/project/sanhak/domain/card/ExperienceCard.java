package com.project.sanhak.domain.card;

import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private LocalDate ECFromDate;
    private LocalDate ECToDate;
    private String ECTitle;
    private String ECPosition;
    private String ECSkill;
    private String ECTool;
    private String ECReflection;
    @Column(columnDefinition = "LONGTEXT")
    private String ECSummary;
    private String ECImageUrl;
    private String ECPdfName;
    private String ECPdfUrl;
    @Lob
    private String ECLink;
    @Column(columnDefinition = "LONGTEXT")
    private String ECText;
}