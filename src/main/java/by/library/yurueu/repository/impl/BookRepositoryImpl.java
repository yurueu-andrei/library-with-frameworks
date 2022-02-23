package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.repository.BookRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Set;

public class BookRepositoryImpl extends AbstractRepositoryImpl<Book> implements BookRepository {
    private static final String TITLE_COLUMN = "title";
    private static final String PAGES_COLUMN = "pages";
    private static final String IMAGE_PATH_COLUMN = "imagePath";
    private static final String BOOK_ID_COLUMN = "bookId";
    private static final String BOOK_COPY_ID_COLUMN = "bookCopyId";

    private static final String SELECT_ALL_QUERY = "from Book";
    private static final String UPDATE_QUERY =
            "UPDATE Book SET title=:title, pages=:pages, imagePath=:imagePath WHERE id=:id";

    private static final String DELETE_BOOK_COPIES_QUERY = "DELETE BookCopy bc WHERE bc.book.id=:bookId";
    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE BookDamage bd WHERE bd.bookCopy.id=:bookCopyId";

    public BookRepositoryImpl() {
        super(Book.class);
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
    protected void constructQuery(Query query, Book book){
        query.setParameter(TITLE_COLUMN, book.getTitle())
                .setParameter(PAGES_COLUMN, book.getPagesNumber())
                .setParameter(IMAGE_PATH_COLUMN, book.getImagePath())
                .setParameter(ID_COLUMN, book.getId());
    }

    @Override
    protected void deleteLinks(Session session, Book book) {
        deleteBookCopyDamage(session, book.getBookCopies());
        deleteBookCopies(session, book);
    }

    private void deleteBookCopyDamage(Session session, Set<BookCopy> bookCopies) {
        bookCopies.forEach(bookCopy ->
                session.createQuery(DELETE_BOOK_DAMAGE_QUERY)
                        .setParameter(BOOK_COPY_ID_COLUMN, bookCopy.getId())
                        .executeUpdate()
        );
    }

    private void deleteBookCopies(Session session, Book book) {
        session.createQuery(DELETE_BOOK_COPIES_QUERY)
                .setParameter(BOOK_ID_COLUMN, book.getId())
                .executeUpdate();
    }
}