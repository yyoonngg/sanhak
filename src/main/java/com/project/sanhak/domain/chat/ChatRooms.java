package com.project.sanhak.domain.chat;

import com.project.sanhak.domain.card.ExperienceCard;
import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ChatRooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CRId;

    @JoinColumn(referencedColumnName = "ECId")
    @ManyToOne
    private ExperienceCard CRecid;

    @JoinColumn(referencedColumnName = "UId")
    @ManyToOne
    private User CRuid;

    private int CRType;
}
