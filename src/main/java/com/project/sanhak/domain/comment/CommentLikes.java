package com.project.sanhak.domain.comment;

import com.project.sanhak.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CommentLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CLId;

    @JoinColumn(referencedColumnName ="CId")
    @ManyToOne
    private Comments CLcid;

    @JoinColumn(referencedColumnName ="UId")
    @ManyToOne
    private User CLuid;
}
