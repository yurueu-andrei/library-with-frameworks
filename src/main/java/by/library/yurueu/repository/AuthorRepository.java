package by.library.yurueu.repository;

import by.library.yurueu.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("from Author a left join fetch a.books b left join fetch b.bookCopies bc where a.id=:id")
    Optional<Author> findById(Long id);
}