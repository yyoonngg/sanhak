package com.project.sanhak.user.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int NId;

    private String NMessage;
    private boolean NIsRead;
    private LocalDateTime NDate;
    private String field;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User user;

}
