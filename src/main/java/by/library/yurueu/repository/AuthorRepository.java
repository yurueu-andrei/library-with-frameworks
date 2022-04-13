package by.library.yurueu.repository;

import by.library.yurueu.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("from Author a left join fetch a.books b left join fetch b.bookCopies bc " +
            "where a.id=:id and not a.deleteStatus='DELETED'")
    Optional<Author> findById(Long id);

    @Query("from Author a where not a.deleteStatus='DELETED'")
    List<Author> findAll();
}