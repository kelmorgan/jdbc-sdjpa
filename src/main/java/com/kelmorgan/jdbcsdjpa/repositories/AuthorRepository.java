package com.kelmorgan.jdbcsdjpa.repositories;

import com.kelmorgan.jdbcsdjpa.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName);

    Stream<Author> findAllByFirstNameNotNull();

    @Async
    Future<Author> queryByFirstName(String firstName);

    @Query("select a from Author a where a.firstName = ?1")
    Author findAuthorWithQuery(String firstName);

    @Query("select a from Author a where a.firstName = :first_name")
    Author findAuthorWithNamedQuery(@Param("first_name") String firstName);

    @Query(value = "select * from author where first_name = :first_name",nativeQuery = true)
    Author findAuthorWithNativeQuery(@Param("first_name") String firstName);
}