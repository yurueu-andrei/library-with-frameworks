package by.library.yurueu.repository;

import by.library.yurueu.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("from Book b left join fetch b.genres left join fetch b.authors left join fetch b.bookCopies where b.id=:id")
    Optional<Book> findById(Long id);
}