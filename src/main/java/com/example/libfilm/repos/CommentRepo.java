package com.example.libfilm.repos;

import com.example.libfilm.dao.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepo extends CrudRepository<Comment, Long> {
    Iterable<Comment> findAllByFilmId(int id);
}