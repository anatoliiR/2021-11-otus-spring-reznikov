package ru.reznikov.personlib;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.reznikov.personlib.dao.BookDaoJdbc;
import ru.reznikov.personlib.domain.Author;
import ru.reznikov.personlib.domain.Book;
import ru.reznikov.personlib.domain.Genre;
import ru.reznikov.personlib.exception.InsertException;
import ru.reznikov.personlib.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BookDaoJdbcTest {



    @Autowired
    private BookDaoJdbc bookDao;


    @DisplayName("Добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        var book = new Book("Igor's Book", new Author(1, ""), new Genre(1, ""));
        Book actualBook = null;
        long bookId = 0;
        try {
            bookId = bookDao.insert(book);

            actualBook = bookDao.getById(bookId);
        } catch (NotFoundException | InsertException e) {
            e.printStackTrace();
        }
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getId()).isEqualTo(bookId);
        assertThat(actualBook.getName()).isEqualTo(book.getName());
        assertThat(actualBook.getAuthor().getId()).isEqualTo(book.getAuthor().getId());
        assertThat(actualBook.getGenre().getId()).isEqualTo(book.getGenre().getId());
    }

    @DisplayName("Возвращать ожидаемую книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        var expectedBook = new Book(1, "Book 1_", new Author(1, "Автор 1!"), new Genre(1, "Genre 1"));

        Book actualBook = null;
        boolean getException = false;
        try {
            actualBook = bookDao.getById(1);
        } catch (NotFoundException e) {
            getException = true;
        }
        assertThat(getException).isFalse();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

    }

    /**/
    @DisplayName("Удалять заданную книгу по ее id")
    @Test
    void shouldCorrectDeletePersonById() {

        assertThatCode(() -> bookDao.getById(1))
                .doesNotThrowAnyException();
        boolean getException = false;

        try {
            bookDao.deleteById(1);
        } catch (NotFoundException e) {
            getException = true;
        }
        assertThat(getException).isFalse();
        assertThatThrownBy(() -> bookDao.getById(1))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("Возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedPersonsList() {
        var expectedBook = new Book(1, "Book 1_", new Author(1, "Автор 1!"), new Genre(1, "Genre 1"));

        List<Book> actualBookList = bookDao.getAll();
        assertThat(actualBookList).contains(expectedBook);

    }
}