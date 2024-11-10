package com.project.sanhak.domain.comment;

import com.project.sanhak.domain.lounge.Lounges;
import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CId;
    private int CReport;
    @JoinColumn(referencedColumnName = "LId")
    @ManyToOne
    private Lounges Clid;
    @JoinColumn(referencedColumnName = "CId")
    @ManyToOne
    private Comments CIsParents;
    @JoinColumn(referencedColumnName = "UId")
    @ManyToOne
    private User Cuid;
}
