package com.project.sanhak.domain.skil.code;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SkilPreque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int SPId;

    @ManyToOne
    @JoinColumn(name = "parent_csid", referencedColumnName = "CSId")
    private CodeSkil SPParentscsid;

    @ManyToOne
    @JoinColumn(name = "child_csid", referencedColumnName = "CSId")
    private CodeSkil SPChildcsid;

    @Embedded
    private SkilPrequeCateFlags spCateFlags;
}