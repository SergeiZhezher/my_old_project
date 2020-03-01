package com.example.libfilm.service;

import com.example.libfilm.additionalyMethods.GetFilmInfo;
import com.example.libfilm.dao.Film;
import com.example.libfilm.repos.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private FilmRepo filmRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public boolean addFilm(Model model, String genre, String filmName, MultipartFile file) throws IOException {

        if (filmRepo.findByFilmName(filmName.replace(" ", "-")) != null) {
            System.out.println("Фильм с таким названиям уже существует");
            return true;
        }

        String fileName = "";
        if (file != null && new File(uploadPath).exists()) {
            fileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "trailer\\" + fileName));
        }


        if (fileName != null && !filmName.isEmpty()) {
//            filmName  = AllMethods.searchСorrectionsMethod(filmName);
            System.out.println("Google refactor not work");
            GetFilmInfo getInfoFilm = new GetFilmInfo(filmName);
            Film film = new Film(getInfoFilm.getFilmName(), getInfoFilm.getDescription(), fileName,
                    getInfoFilm.getFilmFull(), genre, getInfoFilm.getYear(),
                    getInfoFilm.getRating() , getInfoFilm.getVotes());

            filmRepo.save(film);
            model.addAttribute("text",getInfoFilm.getFilmName() + ".jpg");
        }
        return false;
    }
}
