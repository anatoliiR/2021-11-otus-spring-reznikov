package ru.reznikov.personlib.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.reznikov.personlib.domain.Genre;
import ru.reznikov.personlib.exception.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;




    @Override
    public Genre getById(long id) throws NotFoundException {
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            return namedParameterJdbcOperations.queryForObject(
                    "select * from GENRE where id = :id", params, new GenreMapper()
            );
        } catch (
                DataAccessException e) {
            throw new NotFoundException("genre not found");
        }
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("select * from GENRE", new GenreMapper());
    }


    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");


            return new Genre(id, name);
        }
    }
}
