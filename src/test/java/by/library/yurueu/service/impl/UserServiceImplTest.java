package by.library.yurueu.service.impl;

import by.library.yurueu.dto.UserDto;
import by.library.yurueu.dto.impl.UserDtoImpl;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.UserRepository;
import by.library.yurueu.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceImplTest() {
        userService = new UserServiceImpl();
    }

    @Test
    void findByIdTest() throws RepositoryException, ServiceException {
        //given
        Long id = 1L;
        UserDto expected = UserDtoImpl.builder().id(1L).rolesId(new HashSet<>()).orders(new HashSet<>()).build();

        //when
        Mockito.when(userRepository.findById(id)).thenReturn(User.builder().id(1L).build());
        Mockito.when(userRepository.findRolesByUserId(id)).thenReturn(new HashSet<>());
        Mockito.when(userRepository.findOrdersByUserId(id)).thenReturn(new HashSet<>());
        UserDto actual = userService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest() {
    }

    @Test
    void addTest() {
    }

    @Test
    void updateTest() {
    }

    @Test
    void deleteTest() {
    }
}