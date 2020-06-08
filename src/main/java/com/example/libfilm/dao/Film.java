package com.example.libfilm.dao;

import lombok.Data;

import javax.persistence.*;
import java.awt.image.BufferedImage;

@Entity
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String filmName;
    @Column(length = 2048)
    private String description;
    private String trailerName;
    private String linkOriginalFilm;
    private String genre;
    private Integer years;
    private Float rait;
    private Float votes;
    @Transient
    private BufferedImage poster;

}
