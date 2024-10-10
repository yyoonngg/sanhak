package com.project.sanhak.domain.comment;

import com.project.sanhak.domain.board.Boards;
import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CId;
    private int CLikes;
    private int CReport;
    @JoinColumn(referencedColumnName = "BId")
    @ManyToOne
    private Boards Cbid;
    @JoinColumn(referencedColumnName = "CId")
    @ManyToOne
    private Comments CIsParents;
    @JoinColumn(referencedColumnName = "UId")
    @ManyToOne
    private User Cuid;
}
