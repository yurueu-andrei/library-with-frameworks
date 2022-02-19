package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.User;
import by.library.yurueu.repository.AbstractRepository;
import by.library.yurueu.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends AbstractRepositoryImpl<User> implements AbstractRepository<User>, UserRepository {
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String PASSPORT_COLUMN = "passport";
    private static final String EMAIL_COLUMN = "email";
    private static final String ADDRESS_COLUMN = "address";
    private static final String BIRTH_DATE_COLUMN = "birth_date";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
    private static final String INSERT_QUERY =
            "INSERT INTO users (first_name, last_name, passport, email, address, birth_date) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_QUERY =
            "UPDATE users SET first_name=?, last_name=?, passport=?, email=?, address=?, birth_date=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id=?";

    private static final String SELECT_ORDERS_BY_USER_ID_QUERY = "SELECT id FROM orders WHERE user_id=?";
    private static final String DELETE_ROLE_LINKS_QUERY = "DELETE FROM user_role_links WHERE user_id=?";
    private static final String DELETE_ORDERS_QUERY = "DELETE FROM orders WHERE user_id=?";
    private static final String DELETE_ORDER_LINKS_QUERY = "DELETE FROM order_book_copy_links WHERE order_id=?";
    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE FROM book_damage WHERE order_id=?";

    public UserRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getSelectByIdQuery() {
        return SELECT_BY_ID_QUERY;
    }

    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String getInsertQuery() {
        return INSERT_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }

    protected User construct(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(ID_COLUMN));
        user.setFirstName(resultSet.getString(FIRST_NAME_COLUMN));
        user.setLastName(resultSet.getString(LAST_NAME_COLUMN));
        user.setPassportNumber(resultSet.getString(PASSPORT_COLUMN));
        user.setEmail(resultSet.getString(EMAIL_COLUMN));
        user.setAddress(resultSet.getString(ADDRESS_COLUMN));
        user.setBirthDate(resultSet.getDate(BIRTH_DATE_COLUMN).toLocalDate());
        return user;
    }

    protected void settingPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getPassportNumber());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getAddress());
        preparedStatement.setDate(6, Date.valueOf(user.getBirthDate()));
    }

    @Override
    protected void deleteLinks(Connection connection, Long id) throws SQLException {
        deleteUserRoleLinks(connection, id);
        deleteBookDamage(connection, id);
        deleteUserOrders(connection, id);
    }

    private void deleteUserRoleLinks(Connection connection, Long userId) throws SQLException {
        deleteUserLinks(connection, userId, DELETE_ROLE_LINKS_QUERY);
    }

    private void deleteUserLinks(Connection connection, Long id, String query) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private void deleteUserOrders(Connection connection, Long userId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_USER_ID_QUERY)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Long> ordersId = new ArrayList<>();
                while (resultSet.next()) {
                    ordersId.add(resultSet.getLong(1));
                }
                deleteOrdersLinks(connection, ordersId);
                deleteOrders(connection, userId);
            }
        }
    }

    private void deleteOrders(Connection connection, Long userId) throws SQLException {
        deleteUserLinks(connection, userId, DELETE_ORDERS_QUERY);
    }

    private void deleteOrdersLinks(Connection connection, List<Long> orders) throws SQLException {
        for (Long orderId : orders) {
            deleteUserLinks(connection, orderId, DELETE_ORDER_LINKS_QUERY);
        }
    }

    private void deleteBookDamage(Connection connection, Long orderId) throws SQLException {
        deleteUserLinks(connection, orderId, DELETE_BOOK_DAMAGE_QUERY);
    }
}