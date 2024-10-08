package com.project.sanhak.domain.board;

import com.project.sanhak.domain.user.User;
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
    private User Buid;
}
