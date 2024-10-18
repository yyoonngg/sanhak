package com.project.sanhak.domain.chat;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CMId;

    @JoinColumn(referencedColumnName = "CRId")
    @ManyToOne
    private ChatRooms CMcrid;

    private String CMContent;
    private LocalDateTime CMTime;
    private int CMIsUser;
}
