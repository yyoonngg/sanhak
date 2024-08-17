package com.project.sanhak.comment.domain;

import com.project.sanhak.board.domain.Boards;
import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int C_Id;

    private int C_Likes;
    private int C_Report;

    @JoinColumn(name = "B_id")
    @ManyToOne
    private Boards B_id;

    @JoinColumn(name = "C_is_parents")
    @ManyToOne
    private Comment C_is_parents;

    @JoinColumn(name = "U_id")
    @ManyToOne
    private User U_id;
}
