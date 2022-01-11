package ru.reznikov.personlib;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.reznikov.personlib.dao.GenreDaoJdbc;
import ru.reznikov.personlib.domain.Genre;
import ru.reznikov.personlib.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
class GenreDaoJdbcTest {


    @Autowired
    private GenreDaoJdbc genreDao;


    @DisplayName("Возвращать ожидаемый жанр по id")
    @Test
    void shouldReturnExpectedGenreById() {
        var expectedGenre = new Genre(1, "Genre 1");

        Genre actualGenre = null;
        boolean getException = false;
        try {
            actualGenre = genreDao.getById(1);
        } catch (NotFoundException e) {
            getException = true;
        }
        assertThat(getException).isFalse();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);

    }


    @DisplayName("Возвращать ожидаемый список жанров ")
    @Test
    void shouldReturnExpectedPersonsList() {
        var expectedGenre = new Genre(1, "Genre 1");

        List<Genre> actualGenreList = genreDao.getAll();
        assertThat(actualGenreList).contains(expectedGenre).hasSize(14);

    }
}