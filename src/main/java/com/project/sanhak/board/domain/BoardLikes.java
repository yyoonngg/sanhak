package com.project.sanhak.board.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;

@Entity
@Table(name = "B_likes")
public class BoardLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BLId;

    @ManyToOne
    @JoinColumn(name = "B_id")
    private Boards boards;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User user;
}
