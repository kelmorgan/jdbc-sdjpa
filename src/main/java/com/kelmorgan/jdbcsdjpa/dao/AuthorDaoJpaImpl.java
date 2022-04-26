package com.kelmorgan.jdbcsdjpa.dao;

import com.kelmorgan.jdbcsdjpa.domain.Author;
import com.kelmorgan.jdbcsdjpa.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class AuthorDaoJpaImpl implements AuthorDaoJpa {

    private final AuthorRepository repository;


    @Override
    public Author getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return repository.findAuthorByFirstNameAndLastName(firstName,lastName).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return repository.save(author);
    }

    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        Author foundAuthor = repository.getById(author.getId());
        foundAuthor.setFirstName(author.getFirstName());
        foundAuthor.setLastName(author.getLastName());
        return repository.save(foundAuthor);
    }

    @Override
    public void deleteAuthorById(Long id) {
        repository.deleteById(id);
    }
}
