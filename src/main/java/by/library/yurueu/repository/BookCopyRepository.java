package by.library.yurueu.repository;

import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.entity.Order;

import java.util.Set;

public interface BookCopyRepository extends BaseRepository<BookCopy> {
    Book findBookByBookCopyId(Long bookCopyId);
    Set<BookDamage> findBookDamagesByBookCopyId(Long bookCopyId);
    Set<Order> findOrdersByBookCopyId(Long bookCopyId);
}