package com.project.sanhak.mentor.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Mentors")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int M_Id;

    private int M_Cate;
    private String M_AvaTime;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User U_id;
}
