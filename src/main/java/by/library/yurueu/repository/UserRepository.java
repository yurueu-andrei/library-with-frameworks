package by.library.yurueu.repository;

import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.Role;
import by.library.yurueu.entity.User;

import java.util.Set;

public interface UserRepository extends BaseRepository<User> {
    Set<Role> findRolesByUserId(Long userId);
    Set<Order> findOrdersByUserId(Long userId);
    Set<Role> findRolesByRolesId(Set<Long> rolesId);
}