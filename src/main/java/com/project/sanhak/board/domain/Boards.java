package com.project.sanhak.board.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;

@Entity
@Table(name = "Boards")
public class Boards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BId;

    private int BLikes;
    private int BReport;
    private int BView;
    private int BCate;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User user;

}
