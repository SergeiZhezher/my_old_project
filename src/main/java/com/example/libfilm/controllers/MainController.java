package com.example.libfilm.controllers;

import com.example.libfilm.repos.FilmRepo;
import com.example.libfilm.service.FilmService;
import com.example.libfilm.utils.BetaFunctions;
import com.example.libfilm.utils.RegistrationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class MainController implements BetaFunctions {

    private RegistrationHandler registrationHandler = new RegistrationHandler();

    @Autowired
    private FilmRepo filmRepo;

    @Autowired
    private FilmService filmService;

    @GetMapping("/")
    public String mainC(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 8) Pageable pageable) {

        filmService.loadMainPageFilms(model, pageable);
        registrationHandler.errorHandlingAndVerification(model);

        return "main";
    }

    @GetMapping("/{genre}")
    public String filterGenre(Model model, @PathVariable String genre) {

        filmService.loadFilmsByGenre(model, genre);

        return "main";
    }

    @GetMapping("/data")
    @ResponseBody
    public List<String> data(@RequestParam String hint, Model model) {

        List<String> names = filmRepo.testFunction(hint.replaceAll("\\s", "-"));

        return names;
    }

}