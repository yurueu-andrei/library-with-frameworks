package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.repository.AbstractRepository;
import by.library.yurueu.repository.BookDamageRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDamageRepositoryImpl extends AbstractRepositoryImpl<BookDamage> implements AbstractRepository<BookDamage>, BookDamageRepository {
    private static final String ID_COLUMN = "id";
    private static final String IMAGE_PATH_COLUMN = "image_path";
    private static final String USER_ID_COLUMN = "user_id";
    private static final String ORDER_ID_COLUMN = "order_id";
    private static final String BOOK_COPY_ID_COLUMN = "book_copy_id";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM book_damage WHERE id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM book_damage";
    private static final String INSERT_QUERY =
            "INSERT INTO book_damage (image_path, user_id, order_id, book_copy_id) VALUES (?,?,?,?)";
    private static final String UPDATE_QUERY =
            "UPDATE book_damage SET image_path=?, user_id=?, order_id=?, book_copy_id=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM book_damage WHERE id=?";

    public BookDamageRepositoryImpl(DataSource dataSource) {
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

    protected BookDamage construct(ResultSet resultSet) throws SQLException {
        BookDamage bookDamage = new BookDamage();
        bookDamage.setId(resultSet.getLong(ID_COLUMN));
        bookDamage.setImagePath(resultSet.getString(IMAGE_PATH_COLUMN));
        bookDamage.setUserId(resultSet.getLong(USER_ID_COLUMN));
        bookDamage.setOrderId(resultSet.getLong(ORDER_ID_COLUMN));
        bookDamage.setBookCopyId(resultSet.getLong(BOOK_COPY_ID_COLUMN));
        return bookDamage;
    }

    protected void settingPreparedStatement(PreparedStatement preparedStatement, BookDamage bookDamage) throws SQLException {
        preparedStatement.setString(1, bookDamage.getImagePath());
        preparedStatement.setLong(2, bookDamage.getUserId());
        preparedStatement.setLong(3, bookDamage.getOrderId());
        preparedStatement.setLong(4, bookDamage.getBookCopyId());
    }
}