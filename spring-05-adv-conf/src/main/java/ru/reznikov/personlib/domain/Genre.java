package ru.reznikov.personlib.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Genre {
    private  long id;
    private final String name;

    public Genre(long id, String name){
        setId(id);
        this.name=name;
    }
}
