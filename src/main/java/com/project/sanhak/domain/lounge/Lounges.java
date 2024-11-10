package com.project.sanhak.domain.lounge;

import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Lounges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int LId;
    private int LLikes;
    private int LView;
    private int LBadge;
    private int LRoadmap;
    private int LCard;
    private String LName;
    private String LPosition;
    private String LImageURL;

    @JoinColumn(referencedColumnName = "UId")
    @OneToOne
    private User LUid;
}
