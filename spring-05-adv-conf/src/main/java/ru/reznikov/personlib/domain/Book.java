package ru.reznikov.personlib.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Book {
    private long id;
    private final String name;
    private final Author author;
    private final Genre genre;



}
