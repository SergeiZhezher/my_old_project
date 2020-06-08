package com.example.libfilm.service;

import com.example.libfilm.dao.Film;
import com.example.libfilm.repos.FilmRepo;
import com.example.libfilm.utils.BetaFunctions;
import com.example.libfilm.utils.FilmParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    private FilmRepo filmRepo;

    @Value("${upload.path}")
    private String uploadPath;

    private FilmParser filmParser = new FilmParser();
    private Film film = null;


    public boolean addFilm(Model model, String genre, String filmName, MultipartFile file) throws IOException {

        if ( isExistFilm(filmName) || genre == null || filmName.isEmpty() || file == null ) return true;

            filmName = BetaFunctions.spellingСorrections(filmName);

            saveDataFilm(filmName, genre);
            saveTrailer(file);
            savePoster();

            filmRepo.save(film);

            model.addAttribute("text",film.getFilmName() + ".png"); //test

        return false;
    }

    private boolean isExistFilm(String filmName) {
        if (filmRepo.findByFilmName(filmName.replace(" ", "-")) != null) {
            System.out.println("Фильм с таким названиям уже существует");
            return true;
        }
        return false;
    }

    private void saveDataFilm(String filmName, String genre) {
        film = filmParser.getDataFilm(filmName);
        film.setGenre(genre);
    }

    private void saveTrailer(MultipartFile file) throws IOException {
        String fileName = "";
            fileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "trailer\\" + fileName));
            film.setTrailerName(fileName);
    }

    private void savePoster() throws IOException {
        ImageIO.write(film.getPoster(),"png", new File(uploadPath + film.getFilmName().replaceAll("\\s", "-") + ".png"));
    }



}
