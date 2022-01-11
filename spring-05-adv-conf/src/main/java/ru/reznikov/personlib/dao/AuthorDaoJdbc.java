package ru.reznikov.personlib.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.reznikov.personlib.domain.Author;
import ru.reznikov.personlib.exception.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;




    @Override
    public Author getById(long id) throws NotFoundException {
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            return namedParameterJdbcOperations.queryForObject(
                    "select * from AUTHOR where id = :id", params, new AuthorMapper()
            );
        } catch (
                DataAccessException e) {
            throw new NotFoundException("author not found");
        }
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query("select * from AUTHOR", new AuthorMapper());
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");


            return new Author(id, name);
        }
    }
}
