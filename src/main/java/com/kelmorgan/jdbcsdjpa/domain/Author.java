package com.kelmorgan.jdbcsdjpa.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String firstName;
    private  String lastName;

    @Transient
    private List<Book> books = new ArrayList<>();

    @Tolerate
    public Author() {
    }
}