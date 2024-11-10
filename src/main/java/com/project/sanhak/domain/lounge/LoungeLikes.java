package com.project.sanhak.domain.lounge;

import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class LoungeLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int LLId;

    @JoinColumn(referencedColumnName = "LId")
    @ManyToOne
    private Lounges LLlid;

    @JoinColumn(referencedColumnName = "UId")
    @ManyToOne
    private User LLuid;
}
