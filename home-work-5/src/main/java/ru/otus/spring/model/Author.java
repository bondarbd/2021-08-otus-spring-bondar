package ru.otus.spring.model;

import lombok.*;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private long id;
    private String authorName;

    public String getFormattedString(){
        return String.format("Номер: %s, автор: %s", id, authorName);
    }
}
