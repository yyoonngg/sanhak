package com.project.sanhak.board.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Boards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BId;

    private int BLikes;
    private int BReport;
    private int BView;
    private int BCate;

    @JoinColumn(name = "UId")
    @ManyToOne
    private User UId;
}
