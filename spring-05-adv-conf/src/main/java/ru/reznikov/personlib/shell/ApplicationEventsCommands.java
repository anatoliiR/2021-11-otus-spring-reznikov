package ru.reznikov.personlib.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.reznikov.personlib.domain.Author;
import ru.reznikov.personlib.domain.Book;
import ru.reznikov.personlib.domain.Genre;
import ru.reznikov.personlib.exception.AuthorException;
import ru.reznikov.personlib.exception.BookException;
import ru.reznikov.personlib.exception.GenreException;
import ru.reznikov.personlib.services.LibService;

@ShellComponent
@RequiredArgsConstructor

public class ApplicationEventsCommands {

    private final LibService libService;

    @ShellMethod(value = "add book to DB", key = {"add"})
    public String addBook(@ShellOption(help = "name of book") String name, @ShellOption(help = "id of author") long authorId, @ShellOption(help = "id of genre") long genreId) {

        try {
            libService.addBook(name, authorId, genreId);
        } catch (AuthorException e) {
            return String.format("Автор с id %s не найден", authorId);
        } catch (GenreException e) {
            return String.format("Жанр с id %s не найден", genreId);
        } catch (BookException e) {
            return "Ошибка добавления";
        }
        return "Книга добавлена";
    }

    @ShellMethod(value = "update book", key = {"update"})
    public String updateBook(@ShellOption(help = "book id ") long bookId, @ShellOption(help = "name of book") String name, @ShellOption(help = "id of author") long authorId, @ShellOption(help = "id of genre") long genreId) {

        try {
            libService.updateBook(bookId, name, authorId, genreId);
        } catch (AuthorException e) {
            return String.format("Автор с id %s не найден", authorId);
        } catch (GenreException e) {
            return String.format("Жанр с id %s не найден", genreId);
        } catch (BookException e) {
            return String.format("Ошибка обновления. Или книга с id %s не найдена", bookId);
        }
        return "Книга обновлена";
    }


    @ShellMethod(value = "delete book by id ", key = {"delete"})
    public String deleteBook(@ShellOption(help = "book id") long bookId) {

        try {
            libService.deleteBook(bookId);
        } catch (BookException e) {
            return String.format("Книга с id %s не найдена", bookId);

        }
        return "Книга удалена";
    }


    @ShellMethod(value = "Get list of authors", key = {"getAuthors"})
    public String getAuthors() {

        StringBuilder sb = new StringBuilder();
        for (Author author : libService.getAuthors()) {
            sb.append(author).append('\n');
        }

        return sb.toString();
    }

    @ShellMethod(value = "Get list of genres", key = {"getGenres"})
    public String getGenres() {
        StringBuilder sb = new StringBuilder();
        for (Genre genre : libService.getGenres()) {
            sb.append(genre).append('\n');

        }


        return sb.toString();
    }


    @ShellMethod(value = "Get book by id", key = {"getBook"})
    public String getBook(@ShellOption(help = "book id ") long bookId) {

        Book book;
        try {
            book = libService.getBook(bookId);
        } catch (BookException e) {
            return String.format("Книга с id %s не найдена", bookId);

        }

        return String.valueOf(book);
    }

    @ShellMethod(value = "get list of books", key = {"getBooks"})
    public String getBooks() {

        var books = libService.getBooks();
        StringBuilder sb = new StringBuilder();
        books.forEach(book -> sb.append(book).append('\n'));

        return sb.toString();
    }
}
