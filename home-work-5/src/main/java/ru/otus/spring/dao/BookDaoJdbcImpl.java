package ru.otus.spring.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Repository
@AllArgsConstructor
public class BookDaoJdbcImpl implements BookDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Book findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.queryForObject(
                "select b.id, b.title, b.genre_id, b.author_id, a.author_name author_name, g.genre_type genre_type " +
                        "from book b left join author a on b.author_id = a.id " +
                        "left join genre g on b.genre_id = g.id " +
                        "where b.id = :id",
                params, new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcOperations.update(
                "delete from book where id = :id", params
        );
    }

    @Override
    public long create(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("genre_id", book.getGenre().getId());
        params.addValue("author_id", book.getAuthor().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into book (title,genre_id,author_id) values(:title,:genre_id,:author_id)", params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("genre_id", book.getGenre().getId());
        params.addValue("author_id", book.getAuthor().getId());
        jdbcOperations.update("update book set title = :title, genre_id = :genre_id, author_id = :author_id where id = :id", params);
    }

    @Override
    public List<Book> findAll() {
        return jdbcOperations.query("select b.id, b.title, b.genre_id, b.author_id, a.author_name author_name, g.genre_type genre_type " +
                        "from (book b left join author a on b.author_id = a.id) " +
                        "left join genre g on b.genre_id = g.id",
                new BookMapper());
    }

    @Override
    public int count() {
        JdbcOperations jdbcOperations = this.jdbcOperations.getJdbcOperations();
        return jdbcOperations.queryForObject("select count(*) from book", Integer.class);
    }

    public static class BookMapper implements RowMapper<Book>{
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            var author = new Author(
                    resultSet.getLong("author_id"),
                    resultSet.getString("author_name"));
            var genre = new Genre(
                    resultSet.getLong("genre_id"),
                    resultSet.getString("genre_type"));
            return new Book(
                    resultSet.getLong("id"),
                    resultSet.getString("title"),
                    genre, author);
        }
    }
}
