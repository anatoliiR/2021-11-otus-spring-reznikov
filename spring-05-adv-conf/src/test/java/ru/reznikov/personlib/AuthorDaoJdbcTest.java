package ru.reznikov.personlib;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.reznikov.personlib.dao.AuthorDaoJdbc;
import ru.reznikov.personlib.dao.GenreDaoJdbc;
import ru.reznikov.personlib.domain.Author;
import ru.reznikov.personlib.domain.Genre;
import ru.reznikov.personlib.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
class AuthorDaoJdbcTest {


    @Autowired
    private AuthorDaoJdbc authorDao;


    @DisplayName("Возвращать ожидаемого автора по id")
    @Test
    void shouldReturnExpectedGenreById() {
        var expectedAuthor = new Author(1, "Автор 1!");

        Author actualAuthor = null;
        boolean getException = false;
        try {
            actualAuthor = authorDao.getById(1);
        } catch (NotFoundException e) {
            getException = true;
        }
        assertThat(getException).isFalse();
        assertThat(actualAuthor).isNotNull().usingRecursiveComparison().isEqualTo(expectedAuthor);

    }


    @DisplayName("Возвращать ожидаемый список авторов ")
    @Test
    void shouldReturnExpectedPersonsList() {
        var expectedAuthor = new Author(1, "Автор 1!");

       var  actualAuthorList = authorDao.getAll();
        assertThat(actualAuthorList).contains(expectedAuthor).hasSize(9);

    }
}