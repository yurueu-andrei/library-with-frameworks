package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.Order;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.BookCopyRepository;
import by.library.yurueu.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

public class BookCopyRepositoryImpl implements BookCopyRepository {
    private static final String ID_COLUMN = "id";
    private static final String BOOK_COPY_STATUS_COLUMN = "book_copy_status";
    private static final String REGISTRATION_DATE_COLUMN = "registration_date";
    private static final String PRICE_COLUMN = "price";
    private static final String PRICE_PER_DAY_COLUMN = "price_per_day";

    private static final String SELECT_ALL_QUERY = "from BookCopy";
    private static final String UPDATE_QUERY =
            "UPDATE BookCopy SET book_copy_status=:book_copy_status, registration_date=:registration_date, price=:price, " +
                    "price_per_day=:price_per_day WHERE id=:id";

    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE BookDamage WHERE book_copy_id=:book_copy_id";

    @Override
    public BookCopy findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(BookCopy.class, id);
        }
    }

    @Override
    public List<BookCopy> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, BookCopy.class)
                    .list();
        }
    }

    @Override
    public BookCopy add(BookCopy bookCopy) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(bookCopy);
            return bookCopy;
        }
    }

    @Override
    public boolean update(BookCopy bookCopy) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                session.createQuery(UPDATE_QUERY)
                        .setParameter(BOOK_COPY_STATUS_COLUMN, bookCopy.getStatus())
                        .setParameter(REGISTRATION_DATE_COLUMN, bookCopy.getRegistrationDate())
                        .setParameter(PRICE_COLUMN, bookCopy.getPrice())
                        .setParameter(PRICE_PER_DAY_COLUMN, bookCopy.getPricePerDay())
                        .setParameter(ID_COLUMN, bookCopy.getId())
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
                BookCopy bookCopy = session.get(BookCopy.class, id);
                deleteLinks(session, bookCopy);
                session.delete(bookCopy);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
                throw new RepositoryException(getClass().getSimpleName() + " was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    private void deleteLinks(Session session, BookCopy bookCopy) {
        deleteBookCopyOrderLinks(bookCopy, bookCopy.getOrders());
        deleteBookDamage(session, bookCopy);
    }

    private void deleteBookCopyOrderLinks(BookCopy bookCopy, Set<Order> orders) {
        for (Order order : orders) {
            order.getBookCopies().remove(bookCopy);
        }
    }

    private void deleteBookDamage(Session session, BookCopy bookCopy) {
        session.createQuery(DELETE_BOOK_DAMAGE_QUERY)
                .setParameter("book_copy_id", bookCopy.getId())
                .executeUpdate();
    }
}