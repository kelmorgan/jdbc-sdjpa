package com.kelmorgan.jdbcsdjpa.dao;

import com.kelmorgan.jdbcsdjpa.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.kelmorgan.jdbcsdjpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoTemplateImplTest {

    @Autowired
    AuthorDaoTemplate authorDao;


    @Test
    void testDeleteAuthor() {
        authorDao.deleteAuthorById(4L);

        assertThrows(EmptyResultDataAccessException.class, () -> authorDao.getById(4L));
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

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthor() {

        Author author = authorDao.getById(1L);

        assertThat(author).isNotNull();

    }
}