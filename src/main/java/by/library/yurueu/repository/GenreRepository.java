package by.library.yurueu.repository;

import by.library.yurueu.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("from Genre g left join fetch g.books b left join fetch b.bookCopies bc " +
            "where g.id=:id and not g.deleteStatus='DELETED'")
    Optional<Genre> findById(Long id);

    @Query("from Genre g where not g.deleteStatus='DELETED'")
    List<Genre> findAll();
}