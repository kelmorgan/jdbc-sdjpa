package com.kelmorgan.jdbcsdjpa.dao;

import com.kelmorgan.jdbcsdjpa.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AuthorDaoTemplateImpl implements AuthorDaoTemplate {

    private final JdbcTemplate template;

    @Override
    public Author getById(Long id) {
        return template.queryForObject("select * from author where id = ?", getRowMapper(), id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return template.queryForObject("select * from author where first_name = ? and last_name = ?",
                getRowMapper()
                , firstName, lastName);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        template.update("insert into author (first_name,last_name) values (?,?)", author.getFirstName(), author.getLastName());

        Long createdId = template.queryForObject("select last_insert_id()", Long.class);

        return this.getById(createdId);
    }

    @Override
    public Author updateAuthor(Author author) {
        template.update("update author set first_name = ?, last_name = ? where id = ?"
                ,author.getFirstName(),author.getLastName(),author.getId());

        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        template.update("delete from author where id = ? ",id);
    }

    private RowMapper<Author> getRowMapper() {
        return new AuthorMapper();
    }
}
