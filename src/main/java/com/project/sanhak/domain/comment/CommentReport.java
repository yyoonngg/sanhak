package com.project.sanhak.domain.comment;

import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CRId;
    private String CRTime;
    @JoinColumn(referencedColumnName = "CId")
    @ManyToOne
    private Comments CRcid;
    @JoinColumn(referencedColumnName = "UId")
    @ManyToOne
    private User CRuid;
}

