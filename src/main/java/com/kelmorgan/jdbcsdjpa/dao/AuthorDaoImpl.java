package com.kelmorgan.jdbcsdjpa.dao;

import com.kelmorgan.jdbcsdjpa.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@RequiredArgsConstructor
@Component
public class AuthorDaoImpl implements AuthorDao {

    private final DataSource source;

    @Override
    public Author getById(Long id) {
        Connection connection = null;

        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();

            ps = connection.prepareStatement("select  * from author where id =  ?");
            ps.setLong(1, id);

            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                Author author = new Author();
                author.setId(id);
                author.setFirstName(resultSet.getString("first_name"));
                author.setLastName(resultSet.getString("last_name"));
                return author;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();

                if (ps != null) ps.close();

                if (connection != null) connection.close();
            } catch (SQLException e) {

            }
        }

        return null;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        Connection connection = null;

        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();

            ps = connection.prepareStatement("select  * from author where first_name =  ? and last_name = ?");
            ps.setString(1, firstName);
            ps.setString(2, lastName);

            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getAuthorFromRS(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();

                if (ps != null) ps.close();

                if (connection != null) connection.close();
            } catch (SQLException e) {

            }
        }

        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {

        Connection connection = null;

        PreparedStatement ps = null;
        ResultSet resultSet = null;


        try {
            connection = source.getConnection();

            ps = connection.prepareStatement("insert into author (first_name,last_name) values (?,?)");
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.execute();

            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery("select LAST_INSERT_ID()");

            if (resultSet.next()) {
                Long savedId = resultSet.getLong(1);

                return getById(savedId);
            }

            statement.close();

        } catch (SQLException e) {

        } finally {
            try {
                if (resultSet != null) resultSet.close();

                if (ps != null) ps.close();

                if (connection != null) connection.close();
            } catch (SQLException e) {

            }
        }
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {

        Connection connection = null;

        PreparedStatement ps = null;
        ResultSet resultSet = null;


        try {
            connection = source.getConnection();

            ps = connection.prepareStatement("update author set first_name = ? , last_name = ? where id = ?");
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.setLong(3, author.getId());
            ps.execute();


        } catch (SQLException e) {

        } finally {
            try {
                if (ps != null) ps.close();

                if (connection != null) connection.close();
            } catch (SQLException e) {

            }
        }
        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        Connection connection = null;
        PreparedStatement ps = null;

        try{
            connection = source.getConnection();
            ps = connection.prepareStatement("delete from author where id = ?");
            ps.setLong(1,id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                assert ps != null;
                ps.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Author getAuthorFromRS(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getLong("id"));
        author.setFirstName(resultSet.getString("first_name"));
        author.setLastName(resultSet.getString("last_name"));
        return author;
    }
}
