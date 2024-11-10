package com.project.sanhak.domain.lounge;

import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class LoungeView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int LVId;

    @JoinColumn(referencedColumnName = "UId")
    @ManyToOne
    private User LVuid;

    @JoinColumn(referencedColumnName = "LId")
    @ManyToOne
    private Lounges LVlid;
}
