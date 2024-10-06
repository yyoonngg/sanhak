package com.project.sanhak.comment.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CRId;

    private String CRTime;

    @JoinColumn(name = "CId")
    @ManyToOne
    private Comments CId;

    @JoinColumn(name = "UId")
    @ManyToOne
    private User UId;
}

