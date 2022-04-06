package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Role;
import by.library.yurueu.repository.BaseRepositoryTest;
import by.library.yurueu.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RoleRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByIdTest_shouldReturnTheFirstRoleInDB() {
        //given
        Role expected = findRoleForFindById();

        //when
        Optional<Role> role = roleRepository.findById(expected.getId());
        Role actual = role.orElse(Role.builder().build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllRolesList() {
        //given
        List<Role> expected = findRolesForFindAll();

        //when
        List<Role> actual = roleRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedRole() {
        //given
        Role expected = Role.builder().id(3L).roleName("superUser").build();
        Role actual = Role.builder().roleName("superUser").build();

        //when
        actual = roleRepository.saveAndFlush(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, roleRepository.findById(expected.getId()).get());
    }

    @Test
    void updateTest_shouldUpdateRole() {
        //given
        Role role = Role.builder().id(2L).roleName("superUser").build();

        // when
        roleRepository.saveAndFlush(role);
        Optional<Role> foundRole = roleRepository.findById(role.getId());

        //then
        Assertions.assertTrue(foundRole.isPresent());
        Assertions.assertEquals(role, roleRepository.findById(role.getId()).get());
    }
}