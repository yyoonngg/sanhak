package com.project.sanhak.mentor.domain;

import com.project.sanhak.user.domain.User;
import jakarta.persistence.*;

@Entity
@Table(name = "Mentors")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MId;

    private int MCate;
    private String MAvaTime;

    @ManyToOne
    @JoinColumn(name = "U_id")
    private User user;
}
