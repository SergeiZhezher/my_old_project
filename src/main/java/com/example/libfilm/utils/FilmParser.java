package com.example.libfilm.utils;

import com.example.libfilm.dao.Film;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FilmParser {

    private Film film = new Film();
    private List<String> href = new ArrayList<>();

    public Film getDataFilm(String filmName) {

        film.setFilmName(filmName);
        getLinkFilm();

        try {

            Document doc = Jsoup.connect(film.getLinkOriginalFilm()).timeout(25000).get();
            Elements ratingFilm = doc.getElementsByClass("b-post__info_rates kp");
            Elements descriptionFilm = doc.getElementsByClass("b-post__description_text");
            Elements yearFilm = doc.select("a[href^=https://rezka.ag/year/]");

            film.setDescription(descriptionFilm.text());
            film.setYears(Integer.parseInt(yearFilm.text().replaceAll("\\D", "")));

            ratingFilm.forEach(r -> {
                film.setVotes(Float.parseFloat(r.child(2).text().replaceAll("\\D", "")));
                film.setRait(formatRating(Float.parseFloat(r.child(1).text())));
            });

            formatFilmName();
            downloadPosterFilm(doc);
        }
        catch (IOException e) { e.printStackTrace(); }

        return film;
    }

    private void getLinkFilm() { // Генерируется ссылка на фильм данные которого нужно спарсить
        String generatedLinkFilm = "https://rezka.ag/index.php?do=search&subaction=search&q=" + film.getFilmName();
        generatedLinkFilm = generatedLinkFilm.replaceAll("\\s", "+");

        try {

            Document doc = Jsoup.connect(generatedLinkFilm).timeout(25000).get();
            Elements aElements = doc.getElementsByClass("b-content__inline_item-link");

            aElements.forEach(a -> {
                href.add(a.child(0).attr("href"));
            });

            film.setLinkOriginalFilm(href.get(0));
            href.clear();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    private void downloadPosterFilm(Document doc) {
        Elements srcImgFilm = doc.getElementsByClass("b-sidecover");

        srcImgFilm.forEach(s -> {
            try {
                URL url = new URL(s.child(0).child(0).attr("src"));
                BufferedImage image = ImageIO.read(url.openStream());
                film.setPoster(image);
            }
            catch (IOException e) { e.printStackTrace(); }
        });
    }

    private void formatFilmName() {
        film.setFilmName(film.getFilmName().replace(" ", "-"));
    }

    private float formatRating(float rating) {
        rating = Float.parseFloat(String.format("%.1f", rating).replace(',', '.'));
        return rating * film.getVotes();
    }
}



