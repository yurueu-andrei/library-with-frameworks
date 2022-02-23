package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.User;
import by.library.yurueu.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserRepositoryImpl extends AbstractRepositoryImpl<User> implements UserRepository {
    private static final String FIRST_NAME_COLUMN = "firstName";
    private static final String LAST_NAME_COLUMN = "lastName";
    private static final String PASSPORT_COLUMN = "passport";
    private static final String EMAIL_COLUMN = "email";
    private static final String ADDRESS_COLUMN = "address";
    private static final String BIRTH_DATE_COLUMN = "birthDate";
    private static final String USER_ID_COLUMN = "userId";

    private static final String SELECT_ALL_QUERY = "from User";
    private static final String UPDATE_QUERY =
            "UPDATE User " +
                    " SET firstName=:firstName, lastName=:lastName, passport=:passport, " +
                    " email=:email, address=:address, birthDate=:birthDate " +
                    " WHERE id=:id";

    private static final String DELETE_ORDERS_QUERY = "DELETE Order o WHERE o.user.id=:userId";
    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE BookDamage bd WHERE bd.user.id=:userId";

    public UserRepositoryImpl() {
        super(User.class);
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
    protected void constructQuery(Query query, User user){
        query.setParameter(FIRST_NAME_COLUMN, user.getFirstName())
                .setParameter(LAST_NAME_COLUMN, user.getLastName())
                .setParameter(PASSPORT_COLUMN, user.getPassportNumber())
                .setParameter(EMAIL_COLUMN, user.getEmail())
                .setParameter(ADDRESS_COLUMN, user.getAddress())
                .setParameter(BIRTH_DATE_COLUMN, user.getBirthDate())
                .setParameter(ID_COLUMN, user.getId());
    }

    @Override
    protected void deleteLinks(Session session, User user) {
        deleteUserBookDamage(session, user);
        deleteUserOrders(session, user);
    }

    private void deleteUserOrders(Session session, User user) {
        session.createQuery(DELETE_ORDERS_QUERY)
                .setParameter(USER_ID_COLUMN, user.getId())
                .executeUpdate();
    }

    private void deleteUserBookDamage(Session session, User user) {
        session.createQuery(DELETE_BOOK_DAMAGE_QUERY)
                .setParameter(USER_ID_COLUMN, user.getId())
                .executeUpdate();
    }
}