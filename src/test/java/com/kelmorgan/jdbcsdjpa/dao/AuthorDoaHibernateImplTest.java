package com.kelmorgan.jdbcsdjpa.dao;

import com.kelmorgan.jdbcsdjpa.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.kelmorgan.jdbcsdjpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDoaHibernateImplTest {

    @Autowired
    AuthorDoaHibernate authorDao;


    @Test
    void testFindAllAuthors() {
        List<Author> authors = authorDao.findAll();

        System.out.println(authors);
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void testGetAuthorList() {
        List<Author> authors = authorDao.getListAuthorByLastName("Wall");

        System.out.println(authors);
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void testDeleteAuthor() {
        authorDao.deleteAuthorById(4L);

       assertThat(authorDao.getById(4L)).isNull();
    }

    @Test
    void testUpdateAuthor() {

        Author author = authorDao.getById(1L);

        author.setLastName("Kufre");

        Author updatedAuthor = authorDao.updateAuthor(author);

        System.out.println(updatedAuthor);

        assertThat(updatedAuthor).isNotNull();
        assertThat(updatedAuthor.getLastName()).isEqualTo("Kufre");

    }

    @Test
    void saveAuthorTest() {
        Author author = new Author();
        author.setFirstName("Kel");
        author.setLastName("Morgan222");

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

    @Test
    void findAuthorByNativeName() {

        Author author = authorDao.findAuthorByNative("Eric","Evans");

        System.out.println(author);

        assertThat(author).isNotNull();
    }
}