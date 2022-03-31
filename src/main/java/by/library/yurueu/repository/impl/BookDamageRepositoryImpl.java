package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.repository.BookDamageRepository;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

@Repository
public class BookDamageRepositoryImpl extends AbstractRepositoryImpl<BookDamage> implements BookDamageRepository {
    private static final String IMAGE_PATH_COLUMN = "imagePath";
    private static final String DAMAGE_DESCRIPTION_COLUMN = "damageDescription";

    private static final String SELECT_ALL_QUERY = "from BookDamage";
    private static final String UPDATE_QUERY =
            " UPDATE BookDamage " +
                    " SET imagePath=:imagePath, damageDescription=:damageDescription " +
                    " WHERE id=:id";

    public BookDamageRepositoryImpl(EntityManagerFactory factory) {
        super(BookDamage.class, factory.unwrap(SessionFactory.class));
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
    protected void constructQuery(Query query, BookDamage bookDamage) {
        query.setParameter(IMAGE_PATH_COLUMN, bookDamage.getImagePath())
                .setParameter(DAMAGE_DESCRIPTION_COLUMN, bookDamage.getDamageDescription())
                .setParameter(ID_COLUMN, bookDamage.getId());
    }
}