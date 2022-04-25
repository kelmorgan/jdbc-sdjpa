package com.kelmorgan.jdbcsdjpa.dao;

import com.kelmorgan.jdbcsdjpa.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

@Component
@RequiredArgsConstructor
public class AuthorDoaHibernateImpl implements AuthorDoaHibernate {

    private final EntityManagerFactory emf;

    @Override
    public Author getById(Long id) {
        return getEntityManager().find(Author.class,id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {

        TypedQuery<Author> query = getEntityManager().createQuery("select a from Author a " +
                "where a.firstName = :first_name and a.lastName = :last_name ",Author.class);

        query.setParameter("first_name",firstName);
        query.setParameter("last_name",lastName);
        return query.getSingleResult();
    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.flush();
        entityManager.getTransaction().commit();

        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }


    private EntityManager getEntityManager(){
       return emf.createEntityManager();
    }
}
