package com.project.sanhak.board.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "B_likes")
public class BoardLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BL_Id;

    @JoinColumn(name = "B_id")
    @ManyToOne
    private Boards B_id;

    @JoinColumn(name = "U_id")
    @ManyToOne
    private User U_id;
}
