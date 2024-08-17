package com.project.sanhak.comment.domain;

import com.project.sanhak.board.domain.Boards;
import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;

@Entity
@Table(name = "Comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CId;

    private int CLikes;
    private int CReport;
    private String field;

    @ManyToOne
    @JoinColumn(name = "B_id")
    private Boards boards;

    @ManyToOne
    @JoinColumn(name = "C_id_parents")
    private Comment parentComment;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User user;
}
