package com.project.sanhak.comment.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "C_Reports")
public class CommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CR_Id;

    private String CR_Time;

    @JoinColumn(name = "C_id")
    @ManyToOne
    private Comment C_id;

    @JoinColumn(name = "U_id")
    @ManyToOne
    private User U_id;
}

