package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.Order;
import by.library.yurueu.repository.BookCopyRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Set;

public class BookCopyRepositoryImpl extends AbstractRepositoryImpl<BookCopy> implements BookCopyRepository {
    private static final String BOOK_COPY_STATUS_COLUMN = "status";
    private static final String REGISTRATION_DATE_COLUMN = "registrationDate";
    private static final String PRICE_COLUMN = "price";
    private static final String PRICE_PER_DAY_COLUMN = "pricePerDay";
    private static final String BOOK_COPY_ID_COLUMN = "bookCopyId";

    private static final String SELECT_ALL_QUERY = "from BookCopy";
    private static final String UPDATE_QUERY =
            " UPDATE BookCopy " +
            " SET status=:status, registrationDate=:registrationDate, price=:price, pricePerDay=:pricePerDay " +
            " WHERE id=:id";

    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE BookDamage bd WHERE bd.bookCopy.id=:bookCopyId";

    public BookCopyRepositoryImpl() {
        super(BookCopy.class);
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
                .setParameter(PRICE_COLUMN, bookCopy.getPrice())
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
}