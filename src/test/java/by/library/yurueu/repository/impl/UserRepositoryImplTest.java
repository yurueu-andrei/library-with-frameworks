package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.User;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class UserRepositoryImplTest extends BaseRepositoryTest {
    private final UserRepositoryImpl userRepository;

    public UserRepositoryImplTest() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    public void findByIdTest_shouldReturnTheFirstUserInDB() {
        //given
        User expected = findUserForFindById();

        //when
        User actual = userRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllUsers() {
        //given
        List<User> expected = findUsersForFindAll();

        //when
        List<User> actual = userRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedUser() {
        //given
        User expected = User.builder().id(6L).firstName("sergei").lastName("take").passportNumber("1645").email("email235").address("address123").birthDate(LocalDate.of(2002, 5, 5)).build();
        User actual = User.builder().firstName("sergei").lastName("take").passportNumber("1645").email("email235").address("address123").birthDate(LocalDate.of(2002, 5, 5)).build();

        //when
        actual = userRepository.add(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, userRepository.findById(expected.getId()));
    }

    @Test
    void updateTest_shouldUpdateUser() throws RepositoryException {
        //given
        User user = User.builder().id(2L).firstName("sergei").lastName("take").passportNumber("1645").email("email235").address("address123").birthDate(LocalDate.of(2002, 5, 5)).build();

        // when
        boolean isUpdated = userRepository.update(user);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(user, userRepository.findById(user.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteUser() throws RepositoryException {
        //given
        Long userId = 1L;

        // when
        boolean isDeleted = userRepository.delete(userId);

        //then
        Assertions.assertTrue(isDeleted);
    }
}