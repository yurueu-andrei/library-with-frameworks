package by.library.yurueu.repository.impl;

import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.BaseRepository;
import by.library.yurueu.util.HibernateUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractRepositoryImpl<E> implements BaseRepository<E> {
    protected static final String ID_COLUMN = "id";
    private final Class<E> clazz;

    protected abstract String getSelectAllQuery();
    protected abstract String getUpdateQuery();

    protected abstract void constructQuery(Query query, E element);

    public E findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(clazz, id);
        }
    }

    public List<E> findAll() throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(getSelectAllQuery(), clazz).list();
        } catch (Exception ex) {
            throw new RepositoryException(clazz.getSimpleName() + "s were not found[" + ex.getMessage() + "]");
        }
    }

    public E add(E element) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(element);
            return element;
        } catch (Exception ex) {
            throw new RepositoryException(clazz.getSimpleName() + " was not added[" + ex.getMessage() + "]");
        }
    }

    public boolean update(E element) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                Query query = session.createQuery(getUpdateQuery());
                constructQuery(query, element);
                query.executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(clazz.getSimpleName() + " was not updated[" + ex.getMessage() + "]");
            }
        }
    }

    public boolean delete(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                E element = session.get(clazz, id);
                deleteLinks(session, element);
                session.delete(element);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(clazz.getSimpleName() + " was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    protected void deleteLinks(Session session, E element) {}
}