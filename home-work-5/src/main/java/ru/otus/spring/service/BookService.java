package ru.otus.spring.service;

import ru.otus.spring.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(long id);
    void create();
    void update(Book book);
    void deleteById(long id);
    long count();
}
