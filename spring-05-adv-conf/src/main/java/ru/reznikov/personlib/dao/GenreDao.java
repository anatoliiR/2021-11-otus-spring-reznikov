package ru.reznikov.personlib.dao;


import ru.reznikov.personlib.domain.Genre;
import ru.reznikov.personlib.exception.NotFoundException;

import java.util.List;

public interface GenreDao {

    Genre getById(long id) throws NotFoundException;

    List<Genre> getAll();

}
