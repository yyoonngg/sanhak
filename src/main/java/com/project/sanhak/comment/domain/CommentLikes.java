package com.project.sanhak.comment.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CommentLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CL_Id;

    @JoinColumn(name = "CId")
    @ManyToOne
    private Comments CId;

    @JoinColumn(name = "UId")
    @ManyToOne
    private User UId;
}
