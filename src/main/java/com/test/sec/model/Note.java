package com.test.sec.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType   .IDENTITY)
    private Long id;

    @Lob
    private String content;

    private String ownerUsername;
}
