package by.library.yurueu.repository;

import by.library.yurueu.entity.BookDamage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookDamageRepository extends JpaRepository<BookDamage, Long> {
    @Query("from BookDamage bd left join fetch bd.bookCopy left join fetch bd.order left join fetch bd.user " +
            "where bd.id=:id and not bd.status='DELETED'")
    Optional<BookDamage> findById(Long id);

    @Query("from BookDamage bd where not bd.status='DELETED'")
    List<BookDamage> findAll();
}