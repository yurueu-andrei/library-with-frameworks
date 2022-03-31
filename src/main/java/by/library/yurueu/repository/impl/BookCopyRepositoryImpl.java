package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.entity.Order;
import by.library.yurueu.repository.BookCopyRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.Set;

@Repository
public class BookCopyRepositoryImpl extends AbstractRepositoryImpl<BookCopy> implements BookCopyRepository {
    private static final String BOOK_COPY_STATUS_COLUMN = "status";
    private static final String REGISTRATION_DATE_COLUMN = "registrationDate";
    private static final String IMAGE_PATH_COLUMN = "imagePath";
    private static final String PRICE_PER_DAY_COLUMN = "pricePerDay";
    private static final String BOOK_COPY_ID_COLUMN = "bookCopyId";

    private static final String SELECT_ALL_QUERY = "from BookCopy";
    private static final String UPDATE_QUERY =
            " UPDATE BookCopy " +
                    " SET status=:status, registrationDate=:registrationDate, imagePath=:imagePath, pricePerDay=:pricePerDay " +
                    " WHERE id=:id";

    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE BookDamage bd WHERE bd.bookCopy.id=:bookCopyId";

    public BookCopyRepositoryImpl(EntityManagerFactory factory) {
        super(BookCopy.class, factory.unwrap(SessionFactory.class));
    }

    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected void constructQuery(Query query, BookCopy bookCopy) {
        query.setParameter(BOOK_COPY_STATUS_COLUMN, bookCopy.getStatus())
                .setParameter(REGISTRATION_DATE_COLUMN, bookCopy.getRegistrationDate())
                .setParameter(IMAGE_PATH_COLUMN, bookCopy.getImagePath())
                .setParameter(PRICE_PER_DAY_COLUMN, bookCopy.getPricePerDay())
                .setParameter(ID_COLUMN, bookCopy.getId());
    }

    @Override
    protected void deleteLinks(Session session, BookCopy bookCopy) {
        deleteBookCopyOrderLinks(bookCopy, bookCopy.getOrders());
        deleteBookDamage(session, bookCopy);
    }

    private void deleteBookCopyOrderLinks(BookCopy bookCopy, Set<Order> orders) {
        orders.forEach(order -> order.getBookCopies().remove(bookCopy));
    }

    private void deleteBookDamage(Session session, BookCopy bookCopy) {
        session.createQuery(DELETE_BOOK_DAMAGE_QUERY)
                .setParameter(BOOK_COPY_ID_COLUMN, bookCopy.getId())
                .executeUpdate();
    }

    @Override
    public Book findBookByBookCopyId(Long bookCopyId) {
        try (Session session = getSessionFactory().openSession()) {
            BookCopy bookCopy = session.get(BookCopy.class, bookCopyId);
            Hibernate.initialize(bookCopy.getBook());
            return bookCopy.getBook();
        }
    }

    @Override
    public Set<BookDamage> findBookDamagesByBookCopyId(Long bookCopyId) {
        try (Session session = getSessionFactory().openSession()) {
            BookCopy bookCopy = session.get(BookCopy.class, bookCopyId);
            Hibernate.initialize(bookCopy.getBookDamages());
            return bookCopy.getBookDamages();
        }
    }

    @Override
    public Set<Order> findOrdersByBookCopyId(Long bookCopyId) {
        try (Session session = getSessionFactory().openSession()) {
            BookCopy bookCopy = session.get(BookCopy.class, bookCopyId);
            Hibernate.initialize(bookCopy.getOrders());
            return bookCopy.getOrders();
        }
    }
}