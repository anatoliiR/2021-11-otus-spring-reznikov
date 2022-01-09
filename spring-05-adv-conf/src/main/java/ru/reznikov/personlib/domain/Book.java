package ru.reznikov.personlib.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Book {
    private long id;
    private final String name;
    private final Author author;
    private final Genre genre;


    public Book(long id, String name, Author author, Genre genre) {
        setId(id);
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}
