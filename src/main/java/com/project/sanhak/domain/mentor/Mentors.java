package com.project.sanhak.domain.mentor;

import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Mentors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MId;

    private int MCate;
    private String MAvaTime;

    @ManyToOne
    @JoinColumn(name = "UId")
    private User Muid;
}
