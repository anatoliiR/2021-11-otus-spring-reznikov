package ru.reznikov.personlib.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.reznikov.personlib.dao.AuthorDao;
import ru.reznikov.personlib.dao.BookDao;
import ru.reznikov.personlib.dao.GenreDao;
import ru.reznikov.personlib.domain.Author;
import ru.reznikov.personlib.domain.Book;
import ru.reznikov.personlib.domain.Genre;
import ru.reznikov.personlib.exception.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LibServiceImpl implements LibService {
    private final ApplicationContext applicationContext;
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    protected Author getAuthor(long authorId) throws AuthorException {
        try {
            return authorDao.getById(authorId);
        } catch (NotFoundException e) {
            throw new AuthorException();
        }
    }

    protected Genre getGenre(long genreId) throws GenreException {
        try {
            return genreDao.getById(genreId);
        } catch (NotFoundException e) {
            throw new GenreException();
        }
    }

    public void addBook(String bookName, long authorId, long genreId) throws AuthorException, GenreException, BookException {
        try {

            bookDao.insert(new Book(bookName, getAuthor(authorId), getGenre(genreId)));
        } catch (InsertException e) {
            throw new BookException();
        }

    }

    public void updateBook(long bookId, String bookName, long authorId, long genreId) throws AuthorException, GenreException, BookException {

        try {
            bookDao.update(new Book(bookId, bookName, getAuthor(authorId), getGenre(genreId)));
        } catch (NotFoundException | InsertException e) {
            throw new BookException();
        }
    }

    public void deleteBook(long bookId) throws BookException {
        try {
            bookDao.deleteById(bookId);
        } catch (NotFoundException e) {
            throw new BookException();

        }
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getAll();
    }


    @Override
    public Book getBook(long id) throws BookException {

        try {
            return bookDao.getById(id);
        } catch (NotFoundException e) {
            throw new BookException();
        }
    }

    public List<Author> getAuthors() {
        var authorDao = applicationContext.getBean(AuthorDao.class);
        return authorDao.getAll();
    }

    public List<Genre> getGenres() {
        var genreDao = applicationContext.getBean(GenreDao.class);
        return genreDao.getAll();
    }


}
