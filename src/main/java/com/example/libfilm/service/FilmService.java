package com.example.libfilm.service;

import com.example.libfilm.dao.Comment;
import com.example.libfilm.dao.Film;
import com.example.libfilm.dao.User;
import com.example.libfilm.repos.CommentRepo;
import com.example.libfilm.repos.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class FilmService {

    @Autowired
    private FilmRepo filmRepo;

    @Autowired
    private CommentRepo commentRepo;

    public void addComment(Model model, User user, Comment comment, int id, String comments) {
        if (isAuthUser() && !comments.trim().isEmpty()) {
            comment.setAuthor(user);
            comment.setComment(comments);
            comment.setFilmId(id);

            commentRepo.save(comment);
        }
    }

    public void loadMainPageFilms(Model model, Pageable pageable) {
        Page<Film> filmList = filmRepo.findAll(pageable);

        if (filmList != null) {
            loadSliderFilms(model);
            model.addAttribute("filmList", filmList);
        }
    }

    public void loadFilmsByGenre(Model model, String genre) {
        Iterable<Film> genreFilm = filmRepo.findAllByGenre(genre);
        loadSliderFilms(model);

        if (genreFilm != null) model.addAttribute("genreFilm", genreFilm);
    }

    public void loadFilmPage(Model model, String rating, int id) {

        if(!isAuthUser()) lockComment(model);

        loadFilm(model, id);
        loadSliderFilms(model);
        loadComments(model, id);

        updateRating(rating, id);

    }

    private boolean isAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equals("anonymousUser")) return true;

        return false;
    }

    private void lockComment(Model model) {
        model.addAttribute("warning", "WarnFunction()");
    }

    private void loadFilm(Model model, int id) {
        Iterable<Film> film = filmRepo.findById(id);

        loadAdaptedRating(model, film);
        model.addAttribute("filmInfo", film);
    }

    private void loadAdaptedRating(Model model, Iterable<Film> film) {
        film.forEach(f -> {
            float rating = f.getRait();
            rating = (rating / f.getVotes()) / 2;
            rating = Float.parseFloat(String.format("%8.1f", rating).replace(',','.'));
            model.addAttribute("rait", "" + rating);
        });
    }

    private void loadSliderFilms(Model model) {
        Iterable<Film> sliderFilm = filmRepo.findTop21AllByOrderByRaitDesc();
        model.addAttribute("sliderFilm", sliderFilm);
    }

    private void loadComments(Model model, int id) {
        model.addAttribute("cList", commentRepo.findAllByFilmId(id));
    }

    private void updateRating(String rating, int id) {
        if (rating != null) {
            filmRepo.updateRating(id, Float.parseFloat(rating) * 2);
        }
    }
}
