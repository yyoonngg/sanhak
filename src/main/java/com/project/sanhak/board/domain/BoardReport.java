package com.project.sanhak.board.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;

@Entity
@Table(name = "B_reports")
public class BoardReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BRId;

    private String BRTime;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "B_id")
    private Boards boards;
}
