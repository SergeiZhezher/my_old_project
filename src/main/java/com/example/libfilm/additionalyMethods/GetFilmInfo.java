package com.example.libfilm.additionalyMethods;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetFilmInfo {

    private static List<String> href = new ArrayList<>();
    private static String filmName;
    private static String description;
    private static float rating;
    private static float votes;
    private static int year;
    private static String filmFull;

    public static String getFilmName() {
        filmName = filmName.replace(" ", "-");
        return filmName;
    }

    public static String getFilmFull() {
        return filmFull;
    }

    public String getPath() {
        return path;
    }

    private String path = "C:\\Users\\zhezh\\IdeaProjects\\libfilm\\uploads\\";

    public String getDescription() {
        return description;
    }

    public  float getRating() {
        String num = String.format("%.1f", rating).replace(',', '.');
        rating = Float.parseFloat(num);
        return rating * votes;
    }
    public  float getVotes() {
        return votes;
    }

    public  int getYear() {
        return year;
    }

    public GetFilmInfo(String filmName) {
        this.filmName = filmName;
        getLinkFilm();
        parserInfoFilm();

    }
    // Тут генерується силка на фільм з сайта rezka.ag - щоб потім парсить дані
    private void getLinkFilm() {

        String searchLink = "https://rezka.ag/index.php?do=search&subaction=search&q=" + filmName;
        searchLink = searchLink.replaceAll("\\s", "+");

        try {
            Document doc = Jsoup.connect(searchLink).timeout(25000).get();

            Elements aElements = doc.getElementsByClass("b-content__inline_item-link");
            aElements.forEach(a -> {
                href.add(a.child(0).attr("href"));
            });

            filmFull = href.get(0);
            System.out.println(filmFull);
            href.clear();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    //Тут парсяться дані про фільм
    private void parserInfoFilm() {
        try {

            Document doc = Jsoup.connect(filmFull).timeout(25000).get();
            Elements ratingFilm = doc.getElementsByClass("b-post__info_rates kp");
            Elements descriptionFilm = doc.getElementsByClass("b-post__description_text");
            Elements yearFilm = doc.select("a[href^=https://rezka.ag/year/]");
            Elements srcImgFilm = doc.getElementsByClass("b-sidecover");
            description = descriptionFilm.text();
            System.out.println(description);
            year = Integer.parseInt(yearFilm.text().replaceAll("\\D", ""));

            // тут скачується постер для фільма
            srcImgFilm.forEach(s -> {
                try {
                    URL url = new URL("https://rezka.ag/" + s.child(0).child(0).attr("src"));
                    BufferedImage image = ImageIO.read(url.openStream());
                    ImageIO.write(image,"jpg", new File(path + filmName.replaceAll("\\s", "-") + ".jpg"));
                }
                catch (IOException e) { e.printStackTrace(); }

            });

            //тут забіраються рейтинг і кількість людей які проголосували
            ratingFilm.forEach(r -> {

                votes = Float.parseFloat(r.child(2).text().replaceAll("\\D", ""));
                rating = Float.parseFloat(r.child(1).text());

//                System.out.println("rating: " + votes * rating + "\ndescription: " + description + "\nyear: " + year);
            });
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}


