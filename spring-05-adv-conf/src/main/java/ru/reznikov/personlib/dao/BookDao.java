package ru.reznikov.personlib.dao;


import ru.reznikov.personlib.domain.Book;
import ru.reznikov.personlib.exception.InsertException;
import ru.reznikov.personlib.exception.NotFoundException;

import java.util.List;

public interface BookDao {


    long insert(Book book) throws InsertException;

    void update(Book book) throws NotFoundException, InsertException;


    Book getById(long id) throws NotFoundException;

    List<Book> getAll();

    void deleteById(long id) throws NotFoundException;
}
