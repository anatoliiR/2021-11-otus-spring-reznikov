package ru.reznikov.personlib.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
public class Genre {
    private  long id;
    private final String name;


}
