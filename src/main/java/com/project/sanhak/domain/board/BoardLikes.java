package com.project.sanhak.domain.board;

import com.project.sanhak.domain.user.User;
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
    private Boards BLbid;

    @JoinColumn(name = "UId")
    @ManyToOne
    private User BLuid;
}
