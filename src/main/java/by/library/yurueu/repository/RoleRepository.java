package by.library.yurueu.repository;

import by.library.yurueu.entity.Role;
import by.library.yurueu.entity.User;

import java.util.Set;

public interface RoleRepository extends BaseRepository<Role> {
    Set<User> findUsersByRoleId(Long id);
}