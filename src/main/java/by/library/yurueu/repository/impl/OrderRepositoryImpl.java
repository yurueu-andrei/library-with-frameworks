package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Order;
import by.library.yurueu.repository.OrderRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class OrderRepositoryImpl extends AbstractRepositoryImpl<Order> implements OrderRepository {
    private static final String ORDER_STATUS_COLUMN = "orderStatus";
    private static final String START_DATE_COLUMN = "startDate";
    private static final String END_DATE_COLUMN = "endDate";
    private static final String PRICE_COLUMN = "price";
    private static final String ORDER_ID_COLUMN = "orderId";

    private static final String SELECT_ALL_QUERY = "from Order";
    private static final String UPDATE_QUERY =
            " UPDATE Order " +
                    " SET orderStatus=:orderStatus, startDate=:startDate, endDate=:endDate, price=:price " +
                    " WHERE id=:id";

    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE BookDamage bd WHERE bd.order.id=:orderId";

    public OrderRepositoryImpl() {
        super(Order.class);
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
    protected void constructQuery(Query query, Order order) {
        query.setParameter(ORDER_STATUS_COLUMN, order.getOrderStatus())
                .setParameter(START_DATE_COLUMN, order.getStartDate())
                .setParameter(END_DATE_COLUMN, order.getEndDate())
                .setParameter(PRICE_COLUMN, order.getPrice())
                .setParameter(ID_COLUMN, order.getId());
    }

    @Override
    protected void deleteLinks(Session session, Order order) {
        deleteOrderBookDamage(session, order.getId());
    }

    private void deleteOrderBookDamage(Session session, Long id) {
        session.createQuery(DELETE_BOOK_DAMAGE_QUERY)
                .setParameter(ORDER_ID_COLUMN, id)
                .executeUpdate();
    }
}