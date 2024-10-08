package com.project.sanhak.domain.board;

import com.project.sanhak.domain.user.User;
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
    private User BRuid;

    @JoinColumn(name = "BId")
    @ManyToOne
    private Boards BRbid;
}
