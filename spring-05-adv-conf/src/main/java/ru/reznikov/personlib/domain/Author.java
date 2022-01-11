package ru.reznikov.personlib.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
public class Author {
    private  long id;
    private final String name;


}
