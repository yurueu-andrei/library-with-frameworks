package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.BaseEntity;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.AbstractRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractRepositoryImpl<E extends BaseEntity> implements AbstractRepository<E> {
    protected static final String ID_COLUMN = "id";

    private final DataSource dataSource;

    public AbstractRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    protected abstract String getSelectByIdQuery();
    protected abstract String getSelectAllQuery();
    protected abstract String getInsertQuery();
    protected abstract String getUpdateQuery();
    protected abstract String getDeleteQuery();
    protected abstract E construct(ResultSet resultSet) throws SQLException;
    protected abstract void settingPreparedStatement(PreparedStatement preparedStatement, E element) throws SQLException;

    public E findById(Long id) throws RepositoryException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSelectByIdQuery())
        ) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? construct(resultSet) : null;
            }
        } catch (Exception ex) {
            throw new RepositoryException(" User was not found[" + ex.getMessage() + "]");
        }
    }

    public List<E> findAll() throws RepositoryException {
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSelectAllQuery())
        ) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<E> found = new ArrayList<>();
                while (resultSet.next()) {
                    found.add(construct(resultSet));
                }
                return found;
            }
        } catch (Exception ex) {
            throw new RepositoryException("Users were not found[" + ex.getMessage() + "]");
        }
    }

    public E add(E element) throws RepositoryException {
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getInsertQuery(), Statement.RETURN_GENERATED_KEYS)
        ) {
            settingPreparedStatement(preparedStatement, element);
            int value = preparedStatement.executeUpdate();

            if (value == 1) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        element.setId(resultSet.getLong(1));
                    }
                }
            }
            return element;
        } catch (Exception ex) {
            throw new RepositoryException(element.getClass().getSimpleName() + " was not added [" + ex.getMessage() + "]");
        }
    }

    public boolean update(E element) throws RepositoryException {
        int idQueryIndex = findIdPosition(getUpdateQuery());
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getUpdateQuery())
        ) {
            settingPreparedStatement(preparedStatement, element);
            preparedStatement.setLong(idQueryIndex, element.getId());

            return preparedStatement.executeUpdate() == 1;
        } catch (Exception ex) {
            throw new RepositoryException(element.getClass().getSimpleName() + " was not updated [" + ex.getMessage() + "]");
        }
    }

    private int findIdPosition(String query) {
        return (int) query.chars()
                .filter(charId -> charId == '?')
                .count();
    }

    public boolean delete(Long id) throws RepositoryException {
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getDeleteQuery())
        ) {
            preparedStatement.setLong(1, id);
            try {
                connection.setAutoCommit(false);
                deleteLinks(connection, id);
                preparedStatement.executeUpdate();
                connection.commit();
                return true;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (Exception ex) {
            throw new RepositoryException("User was not deleted [" + ex.getMessage() + "]");
        }
    }

    protected void deleteLinks(Connection connection, Long id) throws SQLException {}
}
