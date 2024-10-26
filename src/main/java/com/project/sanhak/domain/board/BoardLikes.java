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

    @JoinColumn(referencedColumnName = "BId")
    @ManyToOne
    private Boards BLbid;

    @JoinColumn(referencedColumnName = "UId")
    @ManyToOne
    private User BLuid;
}
