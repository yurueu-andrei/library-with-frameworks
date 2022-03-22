package by.library.yurueu.repository;

import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;

import java.util.Set;

public interface OrderRepository extends BaseRepository<Order> {
    User findUserByOrderId(Long id);
    Set<BookCopy> findBookCopiesByOrderId(Long id);
    Set<BookDamage> findBookDamagesByOrderId(Long id);
    Set<BookCopy> findBookCopiesByBookCopiesId(Set<Long> bookCopiesId);
}