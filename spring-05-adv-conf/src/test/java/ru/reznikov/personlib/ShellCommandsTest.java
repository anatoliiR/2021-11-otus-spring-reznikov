package ru.reznikov.personlib;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.Assert;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест команд shell ")
@SpringBootTest
class ShellCommandsTest {
/**/

    @Autowired
    private Shell shell;



    private static final String COMMAND_ADD = "add book_Example 1 1";
    private static final String COMMAND_UPDATE = "update 1 newName 1 2";
    private static final String COMMAND_DELETE = "delete 2";
    private static final String COMMAND_AUTHOR = "getAuthors";
    private static final String COMMAND_GENRE = "getGenres";

    private static final String RESULT_ADD = "Книга добавлена";
    private static final String RESULT_UPDATE = "Книга обновлена";
    private static final String RESULT_DELETE = "Книга удалена";
    private static final String RESULT_AUTHOR = "Author";
    private static final int RESULT_AUTHOR_COUNT = 9;
    private static final String RESULT_GENRE = "Genre";
    private static final int RESULT_GENRE_COUNT = 14;







    @DisplayName("должен добавлять книгу")
    @Test
    void shouldAddAuthor() {
        var res =  shell.evaluate(() -> COMMAND_ADD);
        assertThat((String) res).isEqualTo(RESULT_ADD);
    }


    @DisplayName(" должен обновлять книгу")
    @Test
    void shouldUpdateAuthor() {
        var res =  shell.evaluate(() -> COMMAND_UPDATE);
        assertThat((String) res).isEqualTo(RESULT_UPDATE);
    }

    @DisplayName(" должен удалять книгу")
    @Test
    void shouldDeleteAuthor() {
        var res =  shell.evaluate(() -> COMMAND_DELETE);
        assertThat((String) res).isEqualTo(RESULT_DELETE);
    }


    @DisplayName(" должен показывать список авторов")
    @Test
    void shouldShowAuthors() {
        var res =  shell.evaluate(() -> COMMAND_AUTHOR);
        assertThat((String) res).hasLineCount(RESULT_AUTHOR_COUNT).contains(RESULT_AUTHOR);

    }

    @DisplayName(" должен показывать список жанров")
    @Test
    void shouldShowGenres() {
        var res =  shell.evaluate(() -> COMMAND_GENRE);
        assertThat((String) res).hasLineCount(RESULT_GENRE_COUNT).contains(RESULT_GENRE);
    }


    /**/
}