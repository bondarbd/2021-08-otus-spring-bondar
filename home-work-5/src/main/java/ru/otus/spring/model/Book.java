package ru.otus.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private long id;
    private String title;
    private Genre genre;
    private Author author;

    public String getFormattedString(){
        return  String.format("Книга номер: %s - %s, автор: %s, жанр: %s", id, title, author.getAuthorName(), genre.getGenreType());
    }
}
