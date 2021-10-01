package ru.otus.spring.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
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
public class GenreDaoJdbcImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Genre findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.queryForObject("select g.id, g.genre_type from genre g where id = :id", params, new GenreMapper());
    }

    @Override
    public List<Genre> findAllByGenreTypeContain(String subString) {
        Map<String, Object> params = Collections.singletonMap("subString","%" + subString + "%");
        return jdbcOperations.query("select g.id, g.genre_type from genre g where genre_type like :subString", params, new GenreMapper());
    }

    @Override
    public long insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("type", genre.getGenreType());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into genre (genre_type) values(:type)", params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Genre(
                    resultSet.getLong("id"),
                    resultSet.getString("genre_type"));
        }
    }
}
