package ru.reznikov.personlib.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Author {
    private  long id;
    private final String name;

    public Author(long id, String name){
        setId(id);
        this.name=name;
    }
}
