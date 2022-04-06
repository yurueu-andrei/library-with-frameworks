package by.library.yurueu.repository;

import by.library.yurueu.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    @Query("from BookCopy bc left join fetch bc.book b left join fetch bc.bookDamages bd left join fetch bc.orders where bc.id=:id")
    Optional<BookCopy> findById(Long id);
}