package com.example.libfilm.dao;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String filmName;
    @Column(length = 2048)
    private String description;
    private String linkTrailer;
    private String linkFull;
    private String genre;
    private Integer years;
    private Float rait;
    private Float votes;

    public Film() {
    }

    public Film(String filmName, String description, String linkTrailer, String linkFull, String genre, Integer years, Float rait, Float votes) {
        this.filmName = filmName;
        this.description = description;
        this.linkTrailer = linkTrailer;
        this.linkFull = linkFull;
        this.genre = genre;
        this.years = years;
        this.rait = rait;
        this.votes = votes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public  String getFilmName() {

        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLingTrailer() {
        return linkTrailer;
    }

    public void setLingTrailer(String lingTrailer) {
        this.linkTrailer = lingTrailer;
    }

    public String getLinkPoster() {
        return linkFull;
    }

    public void setLinkPoster(String linkPoster) {
        this.linkFull = linkPoster;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Float getRait() {
        return rait;
    }

    public void setRait(Float rait) {
        this.rait = rait;
    }

    public Float getVotes() {
        return votes;
    }

    public void setVotes(Float votes) {
        this.votes = votes;
    }
}
