package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.AuthorRepository;
import by.library.yurueu.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

public class AuthorRepositoryImpl implements AuthorRepository {
    private static final String ID_COLUMN = "id";
    private static final String FIRST_NAME_COLUMN = "firstName";
    private static final String LAST_NAME_COLUMN = "lastName";
    private static final String BIRTH_DATE_COLUMN = "birthDate";
    private static final String IMAGE_PATH_COLUMN = "imagePath";

    private static final String SELECT_ALL_QUERY = "from Author";
    private static final String UPDATE_QUERY =
            "UPDATE Author SET firstName=:firstName, lastName=:lastName, birthDate=:birthDate, imagePath=:imagePath WHERE id=:id";

    @Override
    public Author findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Author.class, id);
        }
    }

    @Override
    public List<Author> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Author.class)
                    .list();
        }
    }

    @Override
    public Author add(Author author) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(author);
            return author;
        }
    }

    @Override
    public boolean update(Author author) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                session.createQuery(UPDATE_QUERY)
                        .setParameter(FIRST_NAME_COLUMN, author.getFirstName())
                        .setParameter(LAST_NAME_COLUMN, author.getLastName())
                        .setParameter(BIRTH_DATE_COLUMN, author.getBirthDate())
                        .setParameter(IMAGE_PATH_COLUMN, author.getImagePath())
                        .setParameter(ID_COLUMN, author.getId())
                        .executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
                throw new RepositoryException(getClass().getSimpleName() + " was not updated[" + ex.getMessage() + "]");

            }
        }
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Author author = session.get(Author.class, id);
                deleteLinks(author);
                session.delete(author);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
                throw new RepositoryException(getClass().getSimpleName() + " was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    private void deleteLinks(Author author) {
        deleteBookGenreLinks(author, author.getBooks());
    }

    private void deleteBookGenreLinks(Author author, Set<Book> books) {
        for (Book book : books) {
            book.getAuthors().remove(author);
        }
    }
}