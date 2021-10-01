package ru.otus.spring.model;

import lombok.*;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    private long id;
    private String genreType;

    public String getFormattedString() {
        return String.format("Номер: %s, жанр: %s", id, genreType);
    }
}
