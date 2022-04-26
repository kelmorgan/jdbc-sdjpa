package com.kelmorgan.jdbcsdjpa.dao;

import com.kelmorgan.jdbcsdjpa.domain.Author;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {

        return null;
//        return Author.builder()
//                .id(rs.getLong("id"))
//                .firstName(rs.getString("first_name"))
//                .lastName(rs.getString("last_name"))
//                .build();
    }
}
