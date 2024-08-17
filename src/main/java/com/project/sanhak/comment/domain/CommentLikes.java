package com.project.sanhak.comment.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;

@Entity
@Table(name = "C_likes")
public class CommentLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CLId;

    @ManyToOne
    @JoinColumn(name = "C_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User user;
}
