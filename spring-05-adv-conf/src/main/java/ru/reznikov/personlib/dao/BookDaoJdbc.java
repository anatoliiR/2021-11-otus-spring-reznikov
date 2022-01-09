package ru.reznikov.personlib.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.reznikov.personlib.domain.Author;
import ru.reznikov.personlib.domain.Book;
import ru.reznikov.personlib.domain.Genre;
import ru.reznikov.personlib.exception.InsertException;
import ru.reznikov.personlib.exception.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        // Это просто оставили, чтобы не переписывать код
        // В идеале всё должно быть на NamedParameterJdbcOperations
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }


    @Override
    public long insert(Book book) throws InsertException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            SqlParameterSource parameters = new MapSqlParameterSource().addValue("name", book.getName())
                    .addValue("author_id", book.getAuthor().getId())
                    .addValue("genre_id", book.getGenre().getId());
            namedParameterJdbcOperations.update("insert into BOOK ( `name`,`author_id`,`genre_id`) values ( :name,:author_id,:genre_id)",
                    parameters, keyHolder);

            return keyHolder.getKeyAs(Long.class);
        } catch (DataAccessException e) {
            throw new InsertException(e.getMessage());
        }
    }

    @Override
    public void update(Book book) throws NotFoundException, InsertException {
        int state;
        try {

            SqlParameterSource parameters = new MapSqlParameterSource().addValue("name", book.getName())
                    .addValue("author_id", book.getAuthor().getId())
                    .addValue("genre_id", book.getGenre().getId())
                    .addValue("id", book.getId());
            state = namedParameterJdbcOperations.update("update BOOK set  `name`= :name, `author_id`= :author_id, `genre_id`= :genre_id  where id = :id",

                    parameters);
        } catch (DataAccessException e) {
            throw new InsertException(e.getMessage());
        }
        if (state == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public Book getById(long id) throws NotFoundException {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return namedParameterJdbcOperations.queryForObject(
                    "select b.`id` as id,  b.`name` as name,b.`author_id` as author_id ,b.`genre_id` as genre_id, a.name as author_name,g.name as genre_name from BOOK b " +
                            "left join author a on(b.author_id=a.id) " +
                            "left join genre g on(b.genre_id=g.id)  " +
                            "where b.id = :id", params, new BookMapper()
            );
        } catch (DataAccessException e) {

            throw new NotFoundException("book not found");
        }
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.`id` as id,  b.`name` as name,b.`author_id` as author_id ,b.`genre_id` as genre_id, " +
                "a.name as author_name," +
                "g.name as genre_name from BOOK b " +
                "left join author a on(b.author_id=a.id) " +
                "left join genre g on(b.genre_id=g.id)  ", new BookMapper());
    }

    @Override
    public void deleteById(long id) throws NotFoundException {

        Map<String, Object> params = Collections.singletonMap("id", id);
        int state = namedParameterJdbcOperations.update(
                "delete from BOOK where id = :id", params
        );
        if (state == 0) {
            throw new NotFoundException("book not found");
        }

    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long authorId = resultSet.getLong("author_id");
            long genreId = resultSet.getLong("genre_id");
            String authorName = resultSet.getString("author_name");
            String genreName = resultSet.getString("genre_name");


            return new Book(id, name, new Author(authorId, authorName), new Genre(genreId, genreName));
        }
    }

}
