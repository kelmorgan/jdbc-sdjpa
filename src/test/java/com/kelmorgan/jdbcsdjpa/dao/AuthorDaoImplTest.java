package com.kelmorgan.jdbcsdjpa.dao;

import com.kelmorgan.jdbcsdjpa.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.kelmorgan.jdbcsdjpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoImplTest {

    @Autowired
    AuthorDao authorDao;


    @Test
    void testDeleteAuthor(){

       authorDao.deleteAuthorById(5L);
    }
    @Test
    void testUpdateAuthor() {

        Author author = new Author();

        author.setFirstName("Kel");
        author.setFirstName("Morgan3");

        Author savedAuthor = authorDao.saveNewAuthor(author);

        savedAuthor.setLastName("Kufre");

        Author updatedAuthor = authorDao.updateAuthor(savedAuthor);


        assertThat(updatedAuthor).isNotNull();
        assertThat(updatedAuthor.getLastName()).isEqualTo("Kufre");

    }

    @Test
    void saveAuthorTest() {
        Author author =new Author();
        author.setFirstName("Kel");
        author.setLastName("Morgan");

        Author savedAuthor = authorDao.saveNewAuthor(author);

        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isNotNull();
    }

    @Test
    void testGetAuthorByName(){
        Author author = authorDao.findAuthorByName("Craig","Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthor() {

        Author author = authorDao.getById(1L);

        assertThat(author).isNotNull();

    }
}