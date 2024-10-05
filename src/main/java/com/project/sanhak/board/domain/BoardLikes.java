package com.project.sanhak.board.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BoardLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BLId;

    @JoinColumn(name = "BId")
    @ManyToOne
    private Boards BId;

    @JoinColumn(name = "UId")
    @ManyToOne
    private User UId;
}
