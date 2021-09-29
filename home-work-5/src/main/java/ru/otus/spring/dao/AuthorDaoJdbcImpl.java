package ru.otus.spring.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.model.Author;

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
public class AuthorDaoJdbcImpl implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Author findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.queryForObject("select a.id, a.author_name from author a where id = :id", params, new AuthorMapper());
    }

    @Override
    public long insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getAuthorName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into author (author_name) values(:name)", params, keyHolder);
        return keyHolder.getKey().longValue();
    }


    @Override
    public List<Author> findAllByAuthorNameContain(String subString) {
        Map<String, Object> params = Collections.singletonMap("subString","%" +  subString + "%");
        return jdbcOperations.query("select a.id, a.author_name from author a where a.author_name like :subString", params, new AuthorMapper());
    }

    public static class AuthorMapper implements RowMapper<Author>{
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(
                    resultSet.getLong("id"),
                    resultSet.getString("author_name"));
        }
    }
}
