package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.User;
import by.library.yurueu.repository.BaseRepositoryTest;
import by.library.yurueu.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstUserInDB() {
        //given
        User expected = findUserForFindById();

        //when
        Optional<User> user = userRepository.findById(expected.getId());
        User actual = user.orElse(User.builder().build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllUsersList() {
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
        actual = userRepository.saveAndFlush(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, userRepository.findById(expected.getId()).get());
    }

    @Test
    void updateTest_shouldUpdateUser() {
        //given
        User user = User.builder().id(2L).firstName("sergei").lastName("take").passportNumber("1645").email("email235").address("address123").birthDate(LocalDate.of(2002, 5, 5)).build();

        // when
        userRepository.saveAndFlush(user);
        Optional<User> foundUser = userRepository.findById(user.getId());

        //then
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(user, userRepository.findById(user.getId()).get());
    }
}