package by.library.yurueu.repository;

import by.library.yurueu.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("from Role r left join fetch r.users where r.id=:id")
    Optional<Role> findById(Long id);
}