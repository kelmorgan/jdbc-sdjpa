package com.kelmorgan.jdbcsdjpa.dao;

import com.kelmorgan.jdbcsdjpa.domain.Author;

import java.util.List;

public interface AuthorDoaHibernate {

    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById (Long id);

    List<Author> getListAuthorByLastName(String lastName);
    List<Author> findAll();


}
