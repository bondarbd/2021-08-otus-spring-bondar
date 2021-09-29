package ru.otus.spring.dao;

import ru.otus.spring.model.Author;

import java.util.List;

public interface AuthorDao  {
    Author findById(long id);
    long insert(Author author);
    List<Author> findAllByAuthorNameContain(String subString);
}
