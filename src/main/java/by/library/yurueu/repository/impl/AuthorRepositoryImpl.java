package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Author;
import by.library.yurueu.repository.AbstractRepository;
import by.library.yurueu.repository.AuthorRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class AuthorRepositoryImpl extends AbstractRepositoryImpl<Author> implements AuthorRepository, AbstractRepository<Author> {
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String BIRTH_DATE_COLUMN = "birth_date";
    private static final String IMAGE_PATH_COLUMN = "image_path";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM authors WHERE id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM authors";
    private static final String INSERT_QUERY =
            "INSERT INTO authors (first_name, last_name, birth_date, image_path) VALUES (?,?,?,?)";
    private static final String UPDATE_QUERY =
            "UPDATE authors SET first_name = ?, last_name = ?, birth_date = ?, image_path = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM authors WHERE id = ?";

    private static final String DELETE_BOOK_AUTHOR_LINKS_QUERY = "DELETE FROM book_author_links WHERE author_id=?";

    public AuthorRepositoryImpl(DataSource dataSource) {
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

    protected Author construct(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getLong(ID_COLUMN));
        author.setFirstName(resultSet.getString(FIRST_NAME_COLUMN));
        author.setLastName(resultSet.getString(LAST_NAME_COLUMN));
        author.setBirthDate(resultSet.getDate(BIRTH_DATE_COLUMN).toLocalDate());
        author.setImagePath(resultSet.getString(IMAGE_PATH_COLUMN));
        return author;
    }

    protected void settingPreparedStatement(PreparedStatement preparedStatement, Author author) throws SQLException {
        preparedStatement.setString(1, author.getFirstName());
        preparedStatement.setString(2, author.getLastName());
        preparedStatement.setDate(3, Date.valueOf(author.getBirthDate()));
        preparedStatement.setString(4, author.getImagePath());
    }

    private void deleteAuthorLinks(Connection connection, Long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(AuthorRepositoryImpl.DELETE_BOOK_AUTHOR_LINKS_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    protected void deleteLinks(Connection connection, Long authorId) throws SQLException {
        deleteAuthorLinks(connection, authorId);
    }

}