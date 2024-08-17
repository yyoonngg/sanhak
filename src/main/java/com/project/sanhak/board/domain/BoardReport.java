package com.project.sanhak.board.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "B_reports")
public class BoardReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BR_Id;

    private String BR_Time;

    @JoinColumn(name = "U_id")
    @ManyToOne
    private User U_id;

    @JoinColumn(name = "B_id")
    @ManyToOne
    private Boards B_id;
}
