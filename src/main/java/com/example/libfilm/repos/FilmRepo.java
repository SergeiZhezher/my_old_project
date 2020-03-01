package com.example.libfilm.repos;

import com.example.libfilm.dao.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FilmRepo extends JpaRepository<Film, Long> {
    Page<Film> findAll(Pageable pageable);

    Iterable<Film> findTop21AllByOrderByRaitDesc();

    Iterable<Film> findAllByGenre(String genre);

    Film findByFilmName(String filmName);

    Iterable<Film> findById(int id);

    @Query(value = "SELECT f.film_name, f.id FROM Film f WHERE f.film_name LIKE %?1% LIMIT 4", nativeQuery = true)
    List<String> testFunction(String filmName);

    @Query(value = "UPDATE film SET rait = rait + ?2 , votes = votes + 1 WHERE id = ?1 ", nativeQuery = true)
    @Modifying
    @Transactional
    void updateRating(int id, float rating);



}
