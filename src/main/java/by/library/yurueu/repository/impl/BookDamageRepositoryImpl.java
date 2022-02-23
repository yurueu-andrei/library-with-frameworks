package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.BookDamageRepository;
import by.library.yurueu.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class BookDamageRepositoryImpl implements BookDamageRepository {
    private static final String ID_COLUMN = "id";
    private static final String IMAGE_PATH_COLUMN = "image_path";
    private static final String DAMAGE_DESCRIPTION_COLUMN = "damage_description";

    private static final String SELECT_ALL_QUERY = "from BookDamage";
    private static final String UPDATE_QUERY =
            "UPDATE BookDamage SET image_path=:image_path, damage_description=:damage_description WHERE id=:id";

    @Override
    public BookDamage findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(BookDamage.class, id);
        }
    }

    @Override
    public List<BookDamage> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, BookDamage.class).list();
        }
    }

    @Override
    public BookDamage add(BookDamage bookDamage) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(bookDamage);
            return bookDamage;
        }
    }

    @Override
    public boolean update(BookDamage bookDamage) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                session.createQuery(UPDATE_QUERY)
                        .setParameter(IMAGE_PATH_COLUMN, bookDamage.getImagePath())
                        .setParameter(DAMAGE_DESCRIPTION_COLUMN, bookDamage.getDamageDescription())
                        .setParameter(ID_COLUMN, bookDamage.getId())
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
                BookDamage bookDamage = session.get(BookDamage.class, id);
                session.delete(bookDamage);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(getClass().getSimpleName() + " was not deleted[" + ex.getMessage() + "]");
            }
        }
    }
}