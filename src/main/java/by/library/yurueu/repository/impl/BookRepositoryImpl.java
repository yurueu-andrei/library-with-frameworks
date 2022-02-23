package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.BookRepository;
import by.library.yurueu.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

public class BookRepositoryImpl implements BookRepository {
    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String PAGES_COLUMN = "pages";
    private static final String IMAGE_PATH_COLUMN = "image_path";

    private static final String SELECT_ALL_QUERY = "from Book";
    private static final String UPDATE_QUERY =
            "UPDATE Book SET title=:title, pages=:pages, image_path=:image_path WHERE id=:id";

    private static final String DELETE_BOOK_COPIES_QUERY = "DELETE BookCopy WHERE book_id=:book_id";
    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE BookDamage WHERE book_copy_id=:book_copy_id";

    @Override
    public Book findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Book.class, id);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Book.class).list();
        }
    }

    @Override
    public Book add(Book book) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(book);
            return book;
        }
    }

    @Override
    public boolean update(Book book) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                session.createQuery(UPDATE_QUERY)
                        .setParameter(TITLE_COLUMN, book.getTitle())
                        .setParameter(PAGES_COLUMN, book.getPagesNumber())
                        .setParameter(IMAGE_PATH_COLUMN, book.getImagePath())
                        .setParameter(ID_COLUMN, book.getId())
                        .executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(getClass().getSimpleName() + " was not updated[" + ex.getMessage() + "]");
            }
        }
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Book book = session.get(Book.class, id);
                deleteLinks(session, book);
                session.delete(book);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(getClass().getSimpleName() + " was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    private void deleteLinks(Session session, Book book) {
        deleteBookCopyDamage(session, book.getBookCopies());
        deleteBookCopies(session, book);
    }

    private void deleteBookCopyDamage(Session session, Set<BookCopy> bookCopies) {
        for (BookCopy bookCopy : bookCopies) {
            session.createQuery(DELETE_BOOK_DAMAGE_QUERY)
                    .setParameter("book_copy_id", bookCopy.getId())
                    .executeUpdate();
        }
    }

    private void deleteBookCopies(Session session, Book book) {
        session.createQuery(DELETE_BOOK_COPIES_QUERY)
                .setParameter("book_id", book.getId())
                .executeUpdate();
    }
}