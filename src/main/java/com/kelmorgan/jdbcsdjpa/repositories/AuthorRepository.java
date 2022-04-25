package com.kelmorgan.jdbcsdjpa.repositories;

import com.kelmorgan.jdbcsdjpa.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}