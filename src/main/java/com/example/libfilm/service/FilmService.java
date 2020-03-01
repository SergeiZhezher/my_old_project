package com.example.libfilm.service;

import com.example.libfilm.dao.Comment;
import com.example.libfilm.dao.Film;
import com.example.libfilm.dao.User;
import com.example.libfilm.repos.CommentRepo;
import com.example.libfilm.repos.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Iterable<Comment> commentIterable = null;
    private String warning = null;
    private String isAuthName = null;


    public void getFilm(Model model, String text, int id) {
        if (text != null) {
            System.out.println(Float.parseFloat(text));
            filmRepo.updateRating(id, Float.parseFloat(text) * 2);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // isAuthorization
        isAuthName = auth.getName();

        //Show film
        Iterable<Film> film = filmRepo.findById(id);
        film.forEach(f -> {
            float rait = f.getRait();
            rait = (rait / f.getVotes()) / 2;
            rait = Float.parseFloat(String.format("%8.1f", rait).replace(',','.'));
            model.addAttribute("rait", "" + rait);
        });
        model.addAttribute("filmInfo", film);

        if(isAuthName == "anonymousUser") {
            warning = "WarnFunction()";
            model.addAttribute("warning", warning);
        }
        //Show Comment
        commentIterable = commentRepo.findAllByFilmId(id);
        model.addAttribute("cList", commentIterable);
        commentIterable = null;
        warning = null;
    }

    public void addMessage(Model model, User user, Comment comment, int id, String comments) {
        if (isAuthName != "anonymousUser" && !comments.trim().isEmpty()) {
            comment.setAuthor(user);
            comment.setComment(comments);
            comment.setFilmId(id);
            commentRepo.save(comment);
        }
    }
}
