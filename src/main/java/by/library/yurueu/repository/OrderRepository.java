package by.library.yurueu.repository;

import by.library.yurueu.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("from Order o left join fetch o.bookCopies left join fetch o.user left join fetch o.bookDamages " +
            "where o.id=:id and not o.status='DELETED'")
    Optional<Order> findById(Long id);

    @Query("from Order o where not o.status='DELETED'")
    List<Order> findAll();
}