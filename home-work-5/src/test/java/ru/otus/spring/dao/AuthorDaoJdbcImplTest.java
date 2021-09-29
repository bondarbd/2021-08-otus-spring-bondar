package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DisplayName("Author DAO")
@Import(AuthorDaoJdbcImpl.class)
class AuthorDaoJdbcImplTest {

    private static final Author EXISTING_AUTHOR = new Author(1, "Александр Пушкин");

    @Autowired
    private AuthorDaoJdbcImpl authorDaoJdbc;

    @DisplayName("Return expected Author by id")
    @Test
    void shouldReturnExpectedAuthorById(){
        Author Author = authorDaoJdbc.findById(1);
        assertThat(Author).isEqualTo(EXISTING_AUTHOR);
    }

    @DisplayName("Return expected list of Author")
    @Test
    void shouldReturnExpectedAuthorList() {
        List<Author> actualAuthorList = authorDaoJdbc.findAllByAuthorNameContain(EXISTING_AUTHOR.getAuthorName());
        assertThat(actualAuthorList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(EXISTING_AUTHOR);
    }

    @DisplayName("Insert book to DB")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = Author.builder().authorName("Федор Достоевский").build();
        long expectedAuthorId = authorDaoJdbc.insert(expectedAuthor);
        expectedAuthor.setId(expectedAuthorId);
        Author actualAuthor = authorDaoJdbc.findById(expectedAuthorId);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}
