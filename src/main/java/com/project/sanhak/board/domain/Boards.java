package com.project.sanhak.board.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Boards")
public class Boards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int B_Id;

    private int B_Likes;
    private int B_Report;
    private int B_View;
    private int B_Cate;

    @JoinColumn(name = "U_id")
    @ManyToOne
    private User U_id;
}
