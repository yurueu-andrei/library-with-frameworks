package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.User;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.UserRepository;
import by.library.yurueu.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static final String ID_COLUMN = "id";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String PASSPORT_COLUMN = "passport";
    private static final String EMAIL_COLUMN = "email";
    private static final String ADDRESS_COLUMN = "address";
    private static final String BIRTH_DATE_COLUMN = "birth_date";

    private static final String SELECT_ALL_QUERY = "from User";
    private static final String UPDATE_QUERY =
            "UPDATE User SET first_name=:first_name, last_name=:last_name, passport=:passport," +
                    " email=:email, address=:address, birth_date=:birth_date WHERE id=:id";

    private static final String DELETE_ORDERS_QUERY = "DELETE Order WHERE user_id=:user_id";
    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE BookDamage WHERE user_id=:user_id";

    public User findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }


    public List<User> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, User.class).list();
        }
    }

    public User add(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(user);
            return user;
        }
    }

    public boolean update(User user) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                session.createQuery(UPDATE_QUERY)
                        .setParameter(FIRST_NAME_COLUMN, user.getFirstName())
                        .setParameter(LAST_NAME_COLUMN, user.getLastName())
                        .setParameter(PASSPORT_COLUMN, user.getPassportNumber())
                        .setParameter(EMAIL_COLUMN, user.getEmail())
                        .setParameter(ADDRESS_COLUMN, user.getAddress())
                        .setParameter(BIRTH_DATE_COLUMN, user.getBirthDate())
                        .setParameter(ID_COLUMN, user.getId())
                        .executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(getClass().getSimpleName() + " was not updated[" + ex.getMessage() + "]");
            }
        }
    }

    public boolean delete(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                User user = session.get(User.class, id);
                deleteLinks(session, user);
                session.delete(user);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(getClass().getSimpleName() + " was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    private void deleteLinks(Session session, User user) {
        deleteUserBookDamage(session, user);
        deleteUserOrders(session, user);
    }

    private void deleteUserOrders(Session session, User user) {
        session.createQuery(DELETE_ORDERS_QUERY)
                .setParameter("user_id", user.getId())
                .executeUpdate();
    }

    private void deleteUserBookDamage(Session session, User user) {
        session.createQuery(DELETE_BOOK_DAMAGE_QUERY)
                .setParameter("user_id", user.getId())
                .executeUpdate();
    }
}