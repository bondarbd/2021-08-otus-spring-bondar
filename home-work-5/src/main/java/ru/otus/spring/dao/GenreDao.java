package ru.otus.spring.dao;

import ru.otus.spring.model.Genre;

import java.util.List;

/**
 * @author Denis Bondar (/bondarbd)
 */
public interface GenreDao{
    Genre findById(long id);
    long insert(Genre genre);
    List<Genre> findAllByGenreTypeContain(String subString);
}
