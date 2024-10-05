package com.project.sanhak.comment.domain;

import com.project.sanhak.board.domain.Boards;
import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CId;

    private int CLikes;
    private int CReport;

    @JoinColumn(name = "Bid")
    @ManyToOne
    private Boards BId;

    @JoinColumn(name = "CIsParents")
    @ManyToOne
    private Comments CIsParents;

    @JoinColumn(name = "UId")
    @ManyToOne
    private User UId;
}
