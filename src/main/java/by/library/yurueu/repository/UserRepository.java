package by.library.yurueu.repository;

import by.library.yurueu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User u left join fetch u.orders left join fetch u.roles " +
            "where u.id=:id and not u.status='DELETED'")
    Optional<User> findById(Long id);

    @Query("from User u where not u.status='DELETED'")
    List<User> findAll();

    @Query("from User u left join fetch u.roles " +
            "where u.email=:email and not u.status='DELETED'")
    Optional<User> findByEmail(String email);
}