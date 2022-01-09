package ru.reznikov.personlib.dao;


import ru.reznikov.personlib.domain.Author;
import ru.reznikov.personlib.exception.NotFoundException;

import java.util.List;

public interface AuthorDao {

    Author getById(long id) throws NotFoundException;

    List<Author> getAll();

}
