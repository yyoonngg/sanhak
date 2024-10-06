package com.project.sanhak.board.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BoardReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BRId;

    private String BRTime;

    @JoinColumn(name = "UId")
    @ManyToOne
    private User UId;

    @JoinColumn(name = "BId")
    @ManyToOne
    private Boards BId;
}
