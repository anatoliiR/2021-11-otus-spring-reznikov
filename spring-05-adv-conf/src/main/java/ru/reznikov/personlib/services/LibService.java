package ru.reznikov.personlib.services;

import ru.reznikov.personlib.domain.Author;
import ru.reznikov.personlib.domain.Book;
import ru.reznikov.personlib.domain.Genre;
import ru.reznikov.personlib.exception.AuthorException;
import ru.reznikov.personlib.exception.BookException;
import ru.reznikov.personlib.exception.GenreException;

import java.util.List;

public interface LibService {
    void addBook(String bookName, long authorId, long genreId) throws AuthorException, GenreException, BookException;

    void updateBook(long bookId, String bookName, long authorId, long genreId) throws BookException, AuthorException, GenreException;

    void deleteBook(long bookId) throws BookException;

    Book getBook(long id) throws BookException;

    List<Book> getBooks();

    List<Author> getAuthors();

    List<Genre> getGenres();


}
