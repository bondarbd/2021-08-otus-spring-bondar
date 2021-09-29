package ru.otus.spring.dao;

import ru.otus.spring.model.Book;

import java.util.List;

public interface BookDao {
    Book findById(long id);
    List<Book> findAll();
    long create(Book book);
    void update(Book book);
    int count();
    void deleteById(long id);
}
