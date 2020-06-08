package com.example.libfilm.controllers;

import com.example.libfilm.dao.Comment;
import com.example.libfilm.dao.User;
import com.example.libfilm.repos.FilmRepo;
import com.example.libfilm.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FilmController {

    @Autowired
    FilmRepo filmRepo;

    @Autowired
    FilmService filmService;

    @GetMapping("/film")
    public String view(Model model) {
        return "redirect:/";
    }

    @GetMapping(value = "/film/{id}")
    public String film( Model model, String text, @PathVariable() int id ) {
         filmService.loadFilmPage(model, text, id);

         return "film";
    }

    @PostMapping("/film/{id}")
    public String addMessage(@AuthenticationPrincipal User user, Comment comment, Model model, @PathVariable() int id, @RequestParam String comments) {
            filmService.addComment(model, user, comment, id, comments);

              return "redirect:/film/" + id;
    }
}
