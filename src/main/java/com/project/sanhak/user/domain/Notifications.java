package com.project.sanhak.user.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int N_Id;

    private String N_Message;
    private boolean N_IsRead;
    private LocalDateTime N_Date;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User U_id;
}
