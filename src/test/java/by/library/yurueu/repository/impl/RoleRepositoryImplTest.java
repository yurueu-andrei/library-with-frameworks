package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Role;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.BaseRepositoryTest;
import by.library.yurueu.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByIdTest_shouldReturnTheFirstRoleInDB() throws RepositoryException {
        //given
        Role expected = findRoleForFindById();

        //when
        Role actual = roleRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllRolesList() throws RepositoryException {
        //given
        List<Role> expected = findRolesForFindAll();

        //when
        List<Role> actual = roleRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedRole() throws RepositoryException {
        //given
        Role expected = Role.builder().id(3L).roleName("superUser").build();
        Role actual = Role.builder().roleName("superUser").build();

        //when
        actual = roleRepository.add(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, roleRepository.findById(expected.getId()));
    }

    @Test
    void updateTest_shouldUpdateRole() throws RepositoryException {
        //given
        Role role = Role.builder().id(2L).roleName("superUser").build();

        // when
        boolean isUpdated = roleRepository.update(role);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(role, roleRepository.findById(role.getId()));
    }

    @Test
    void deleteTest_shouldDeleteRole() throws RepositoryException {
        //given
        Long roleId = 1L;

        // when
        boolean isDeleted = roleRepository.delete(roleId);

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertThrows(RepositoryException.class, () -> roleRepository.findById(roleId));
    }
}