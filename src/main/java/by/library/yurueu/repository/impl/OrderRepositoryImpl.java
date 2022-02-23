package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Order;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.OrderRepository;
import by.library.yurueu.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private static final String ID_COLUMN = "id";
    private static final String ORDER_STATUS_COLUMN = "order_status";
    private static final String START_DATE_COLUMN = "start_date";
    private static final String END_DATE_COLUMN = "end_date";
    private static final String PRICE_COLUMN = "price";

    private static final String SELECT_ALL_QUERY = "from Order";
    private static final String UPDATE_QUERY =
            "UPDATE Order SET order_status=:order_status, start_date=:start_date, end_date=:end_date, price=:price WHERE id=:id";

    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE BookDamage WHERE order_id=:order_id";

    @Override
    public Order findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Order.class, id);
        }
    }

    @Override
    public List<Order> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Order.class).list();
        }
    }

    @Override
    public Order add(Order order) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(order);
            return order;
        }
    }

    @Override
    public boolean update(Order order) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                session.createQuery(UPDATE_QUERY)
                        .setParameter(ORDER_STATUS_COLUMN, order.getOrderStatus())
                        .setParameter(START_DATE_COLUMN, order.getStartDate())
                        .setParameter(END_DATE_COLUMN, order.getEndDate())
                        .setParameter(PRICE_COLUMN, order.getPrice())
                        .setParameter(ID_COLUMN, order.getId())
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
                Order order = session.get(Order.class, id);
                deleteLinks(session, order);
                session.delete(order);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(getClass().getSimpleName() + " was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    private void deleteLinks(Session session, Order order) {
        deleteOrderBookDamage(session, order.getId());
    }

    private void deleteOrderBookDamage(Session session, Long id) {
        session.createQuery(DELETE_BOOK_DAMAGE_QUERY)
                .setParameter("order_id", id)
                .executeUpdate();
    }
}