package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.Genre;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.GenreRepository;
import by.library.yurueu.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

public class GenreRepositoryImpl implements GenreRepository {
    private static final String ID_COLUMN = "id";
    private static final String GENRE_NAME_COLUMN = "genre_name";

    private static final String SELECT_ALL_QUERY = "from Genre";
    private static final String UPDATE_QUERY = "UPDATE Genre SET genre_name=:genre_name WHERE id=:id";

    @Override
    public Genre findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Genre.class, id);
        }
    }

    @Override
    public List<Genre> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Genre.class).list();
        }
    }

    @Override
    public Genre add(Genre genre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(genre);
            return genre;
        }
    }

    @Override
    public boolean update(Genre genre) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                session.createQuery(UPDATE_QUERY)
                        .setParameter(GENRE_NAME_COLUMN, genre.getGenreName())
                        .setParameter(ID_COLUMN, genre.getId())
                        .executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
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
                Genre genre = session.get(Genre.class, id);
                deleteLinks(genre);
                session.delete(genre);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(getClass().getSimpleName() + " was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    private void deleteLinks(Genre genre) {
        deleteBookGenreLinks(genre, genre.getBooks());
    }

    private void deleteBookGenreLinks(Genre genre, Set<Book> books) {
        for (Book book : books) {
            book.getGenres().remove(genre);
        }
    }
}