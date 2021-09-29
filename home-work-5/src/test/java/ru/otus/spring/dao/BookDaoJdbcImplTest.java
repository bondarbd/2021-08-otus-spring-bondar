package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(BookDaoJdbcImpl.class)
@DisplayName("Book dao")
class BookDaoJdbcImplTest {

    private static final int EXPECTED_BOOK_COUNT = 2;
    private static final long EXISTING_BOOK_ID = 1;
    private static final Genre EXISTING_GENRE_POEM = new Genre(2, "Поэма");
    private static final Genre EXISTING_GENRE_NOVEL = new Genre(1, "Роман");
    private static final Author EXISTING_AUTHOR = new Author(1, "Александр Пушкин");
    private static final Book EXISTING_BOOK_1 = Book.builder()
            .id(1)
            .title("Руслан и Людмила")
            .author(EXISTING_AUTHOR)
            .genre(EXISTING_GENRE_POEM)
            .build();
    private static final Book EXISTING_BOOK_2 = Book.builder()
            .id(2)
            .title("Евгений Онегин")
            .author(EXISTING_AUTHOR)
            .genre(EXISTING_GENRE_NOVEL)
            .build();

    @Autowired
    private BookDaoJdbcImpl bookDaoJdbc;

    @DisplayName("Return expected count of books")
    @Test
    void shouldReturnExpectedBookCount() {
        int actualPersonsCount = bookDaoJdbc.count();
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("Return expected book by id")
    @Test
    void shouldReturnExpectedBooById(){
        Book actualBook = bookDaoJdbc.findById(1);
        assertThat(actualBook).isEqualTo(EXISTING_BOOK_1);
    }

    @DisplayName("Delete existing book by id value")
    @Test
    void shouldCorrectDeletePersonById() {
        assertThatCode(() -> bookDaoJdbc.findById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDaoJdbc.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDaoJdbc.findById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Insert book to DB")
    @Test
    void shouldInsertBook() {
        Book expectedBook = Book.builder()
                .title("Сказки")
                .author(EXISTING_AUTHOR)
                .genre(EXISTING_GENRE_POEM)
                .build();
        long expectedBookId = bookDaoJdbc.create(expectedBook);
        expectedBook.setId(expectedBookId);
        Book actualBook = bookDaoJdbc.findById(expectedBookId);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Update book to DB")
    @Test
    void shouldUpdateBook() {
        Book updatedBook = Book.builder()
                .id(1)
                .title("Сказки")
                .author(EXISTING_AUTHOR)
                .genre(EXISTING_GENRE_POEM)
                .build();
        bookDaoJdbc.update(updatedBook);
        Book actualBook = bookDaoJdbc.findById(1);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(updatedBook);
    }

    @DisplayName("Return expected list of book")
    @Test
    void shouldReturnExpectedBookList() {
        List<Book> actualBookList = bookDaoJdbc.findAll();
        assertThat(actualBookList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(EXISTING_BOOK_1, EXISTING_BOOK_2);
    }
}
