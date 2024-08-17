package com.project.sanhak.comment.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "C_likes")
public class CommentLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CL_Id;

    @JoinColumn(name = "C_id")
    @ManyToOne
    private Comment C_id;

    @JoinColumn(name = "U_id")
    @ManyToOne
    private User U_id;
}
