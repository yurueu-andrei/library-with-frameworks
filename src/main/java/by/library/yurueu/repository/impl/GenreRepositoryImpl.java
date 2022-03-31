package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.Genre;
import by.library.yurueu.repository.GenreRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.Set;

@Repository
public class GenreRepositoryImpl extends AbstractRepositoryImpl<Genre> implements GenreRepository {
    private static final String GENRE_NAME_COLUMN = "genreName";

    private static final String SELECT_ALL_QUERY = "from Genre";
    private static final String UPDATE_QUERY = "UPDATE Genre SET genreName=:genreName WHERE id=:id";

    public GenreRepositoryImpl(EntityManagerFactory factory) {
        super(Genre.class, factory.unwrap(SessionFactory.class));
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
    protected void constructQuery(Query query, Genre genre) {
        query.setParameter(GENRE_NAME_COLUMN, genre.getGenreName())
                .setParameter(ID_COLUMN, genre.getId());
    }

    @Override
    protected void deleteLinks(Session session, Genre genre) {
        deleteBookGenreLinks(genre, genre.getBooks());
    }

    private void deleteBookGenreLinks(Genre genre, Set<Book> books) {
        books.forEach(book -> book.getGenres().remove(genre));
    }

    @Override
    public Set<Book> findBooksByGenreId(Long genreId) {
        try (Session session = getSessionFactory().openSession()) {
            Genre genre = session.get(Genre.class, genreId);
            Hibernate.initialize(genre.getBooks());
            return genre.getBooks();
        }
    }
}