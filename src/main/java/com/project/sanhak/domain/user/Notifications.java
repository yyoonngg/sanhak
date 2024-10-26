package com.project.sanhak.domain.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int NId;

    private String NMessage;
    private boolean NIsRead;
    private LocalDateTime NDate;

    @ManyToOne
    @JoinColumn(referencedColumnName = "UId")
    private User Nuid;
}
