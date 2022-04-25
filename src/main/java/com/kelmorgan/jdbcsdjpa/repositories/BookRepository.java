package com.kelmorgan.jdbcsdjpa.repositories;

import com.kelmorgan.jdbcsdjpa.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}