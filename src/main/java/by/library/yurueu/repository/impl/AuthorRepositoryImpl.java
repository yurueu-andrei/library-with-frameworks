package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.repository.AuthorRepository;
import by.library.yurueu.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Set;

public class AuthorRepositoryImpl extends AbstractRepositoryImpl<Author> implements AuthorRepository {
    private static final String FIRST_NAME_COLUMN = "firstName";
    private static final String LAST_NAME_COLUMN = "lastName";
    private static final String BIRTH_DATE_COLUMN = "birthDate";
    private static final String IMAGE_PATH_COLUMN = "imagePath";

    private static final String SELECT_ALL_QUERY = "from Author";
    private static final String UPDATE_QUERY =
            " UPDATE Author " +
                    " SET firstName=:firstName, lastName=:lastName, birthDate=:birthDate, imagePath=:imagePath " +
                    " WHERE id=:id";

    public AuthorRepositoryImpl() {
        super(Author.class);
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
    protected void constructQuery(Query query, Author author) {
        query.setParameter(FIRST_NAME_COLUMN, author.getFirstName())
                .setParameter(LAST_NAME_COLUMN, author.getLastName())
                .setParameter(BIRTH_DATE_COLUMN, author.getBirthDate())
                .setParameter(IMAGE_PATH_COLUMN, author.getImagePath())
                .setParameter(ID_COLUMN, author.getId());
    }

    @Override
    protected void deleteLinks(Session session, Author author) {
        deleteBookGenreLinks(author, author.getBooks());
    }

    private void deleteBookGenreLinks(Author author, Set<Book> books) {
        books.forEach(book -> book.getAuthors().remove(author));
    }

    @Override
    public Set<Book> findBooksByAuthorId(Long authorId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Author author = session.get(Author.class, authorId);
            Hibernate.initialize(author.getBooks());
            return author.getBooks();
        }
    }
}