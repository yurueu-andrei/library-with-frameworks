package by.library.yurueu.repository.impl;

import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.BaseRepository;
import by.library.yurueu.util.HibernateUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractRepositoryImpl<E> implements BaseRepository<E> {
    protected static final String ID_COLUMN = "id";
    private final Class<E> clazz;

    protected abstract String getSelectAllQuery();
    protected abstract String getUpdateQuery();

    protected abstract void constructQuery(Query query, E element);

    public E findById(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            E element = session.get(clazz, id);
            if(element == null) {
                throw new RepositoryException(String.format("%s was not found", clazz.getSimpleName()));
            }
            return element;
        }
    }

    public List<E> findAll() throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<E> elements = session.createQuery(getSelectAllQuery(), clazz).list();
            if(elements.isEmpty()) {
                throw new RepositoryException(String.format("%ss were not found", clazz.getSimpleName()));
            }
            return elements;
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