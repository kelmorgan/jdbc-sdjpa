package com.kelmorgan.jdbcsdjpa.dao;

import com.kelmorgan.jdbcsdjpa.domain.Author;
import com.kelmorgan.jdbcsdjpa.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityNotFoundException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.kelmorgan.jdbcsdjpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoJpaImplTest {

    @Autowired
    AuthorDaoJpa authorDao;

    @Autowired
    AuthorRepository repository;

    @Test
    void testAuthorNativeQuery() {
        Author author = repository.findAuthorWithNativeQuery("Eric");
        System.out.println(author);
        assertThat(author).isNotNull();
    }

    @Test
    void testAuthorNameQuery() {
        Author author = repository.findAuthorWithNamedQuery("Eric");
        System.out.println(author);
        assertThat(author).isNotNull();
    }

    @Test
    void testAuthorQuery() {
        Author author = repository.findAuthorWithQuery("Eric");

        System.out.println(author);
        assertThat(author).isNotNull();
    }


    @Test
    void testAuthorFuture() throws ExecutionException, InterruptedException {
        Future<Author> authorFuture = repository.queryByFirstName("Robert");

        Author author = authorFuture.get();

        System.out.println(author);
        assertNotNull(author);
    }

    @Test
    void testAuthorStream() {
        AtomicInteger count = new AtomicInteger();

        repository.findAllByFirstNameNotNull()
                .forEach(author -> count.incrementAndGet());

        assertThat(count.get()).isGreaterThan(1);

    }

    @Test
    void testDeleteAuthor() {
        authorDao.deleteAuthorById(3L);

        assertThrows(JpaObjectRetrievalFailureException.class, () -> authorDao.getById(3L));
    }

    @Test
    void testUpdateAuthor() {

        Author author = authorDao.getById(1L);

        author.setLastName("Kufre Godwin");

        Author updatedAuthor = authorDao.updateAuthor(author);

        System.out.println(updatedAuthor);

        assertThat(updatedAuthor).isNotNull();
        assertThat(updatedAuthor.getLastName()).isEqualTo("Kufre Godwin");

    }

    @Test
    void saveAuthorTest() {
        Author author = new Author();
        author.setFirstName("Kel");
        author.setLastName("Morgan2226");

        Author savedAuthor = authorDao.saveNewAuthor(author);

        System.out.println(savedAuthor);
        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDao.findAuthorByName("Craig", "Walls");

        System.out.println(author);
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByNameNotFound() {
        assertThrows(EntityNotFoundException.class, () -> authorDao.findAuthorByName("kufre", "godwin"));
    }


    @Test
    void testGetAuthor() {

        Author author = authorDao.getById(1L);


        assertThat(author).isNotNull();

    }

}