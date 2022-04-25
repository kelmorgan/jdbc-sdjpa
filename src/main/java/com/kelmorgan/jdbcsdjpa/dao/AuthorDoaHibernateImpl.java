package com.kelmorgan.jdbcsdjpa.dao;

import com.kelmorgan.jdbcsdjpa.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorDoaHibernateImpl implements AuthorDoaHibernate {

    private final EntityManagerFactory emf;

    @Override
    public Author getById(Long id) {
        return getEntityManager().find(Author.class, id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {

//        TypedQuery<Author> query = getEntityManager().createQuery("select a from Author a " +
//                "where a.firstName = :first_name and a.lastName = :last_name ", Author.class);

        TypedQuery<Author> query = getEntityManager().createNamedQuery("find_by_name", Author.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);
        return query.getSingleResult();
    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();

        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager em = getEntityManager();
        em.joinTransaction();
        em.merge(author);
        em.flush();
        em.clear();
        em.close();
        return getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager em = getEntityManager();

        Author author = em.find(Author.class, id);
        em.getTransaction().begin();
        em.remove(author);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Author> getListAuthorByLastName(String lastName) {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Author> query = em.createQuery("select a from Author a where a.lastName like :last_name",Author.class);
            query.setParameter("last_name", lastName + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Author> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Author> typedQuery = em.createNamedQuery("author_find_all",Author.class);

            return typedQuery.getResultList();
        }
        finally {
            em.close();
        }

    }


    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
