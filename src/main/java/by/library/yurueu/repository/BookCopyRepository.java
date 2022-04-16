package by.library.yurueu.repository;

import by.library.yurueu.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    @Query("from BookCopy bc left join fetch bc.book b left join fetch b.genres left join fetch b.authors " +
            "left join fetch bc.bookDamages bd left join fetch bc.orders " +
            "where bc.id=:id and not bc.status='DELETED'")
    Optional<BookCopy> findById(Long id);

    @Query("from BookCopy bc where not bc.status='DELETED'")
    List<BookCopy> findAll();
}