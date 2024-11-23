package com.project.sanhak.domain.user;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PRid;

    @OneToOne
    @JoinColumn(referencedColumnName = "UId")
    private User PRuid;

    private String PRname;
    private String PRemail;
    private String PRbio;
    private String PRprofileImgURL;
}