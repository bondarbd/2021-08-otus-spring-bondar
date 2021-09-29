package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GenreDaoJdbcImpl.class)
class GenreDaoJdbcImplTest {

    private static final Genre EXISTING_GENRE_NOVEL = new Genre(1, "Роман");

    @Autowired
    private GenreDaoJdbcImpl genreDaoJdbc;

    @DisplayName("Return expected genre by id")
    @Test
    void shouldReturnExpectedGenreById(){
        Genre genre = genreDaoJdbc.findById(1);
        assertThat(genre).isEqualTo(EXISTING_GENRE_NOVEL);
    }

    @DisplayName("Return expected list of genre")
    @Test
    void shouldReturnExpectedGenreList() {
        List<Genre> actualGenreList = genreDaoJdbc.findAllByGenreTypeContain(EXISTING_GENRE_NOVEL.getGenreType());
        assertThat(actualGenreList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(EXISTING_GENRE_NOVEL);
    }

    @DisplayName("Insert book to DB")
    @Test
    void shouldInsertBook() {
        Genre expectedGenre = Genre.builder().genreType("Детектив").build();
        long expectedGenreId = genreDaoJdbc.insert(expectedGenre);
        expectedGenre.setId(expectedGenreId);
        Genre actualGenre = genreDaoJdbc.findById(expectedGenreId);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}
