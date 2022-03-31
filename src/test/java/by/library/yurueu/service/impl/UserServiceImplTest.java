package by.library.yurueu.service.impl;

import by.library.yurueu.dto.UserDto;
import by.library.yurueu.dto.UserListDto;
import by.library.yurueu.dto.UserSaveDto;
import by.library.yurueu.dto.UserUpdateDto;
import by.library.yurueu.entity.Role;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findByIdTest_shouldReturnUserDto() throws RepositoryException, ServiceException {
        //given
        Long id = 1L;
        UserDto expected = UserDto.builder().id(id).rolesId(new ArrayList<>()).orders(new ArrayList<>()).build();

        //when
        when(userRepository.findById(id)).thenReturn(User.builder().id(id).build());
        when(userRepository.findRolesByUserId(id)).thenReturn(new HashSet<>());
        when(userRepository.findOrdersByUserId(id)).thenReturn(new HashSet<>());
        UserDto actual = userService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfUserListDto() throws RepositoryException, ServiceException {
        //given
        List<UserListDto> expected = new ArrayList<>() {{
            add(UserListDto.builder().id(1L).build());
            add(UserListDto.builder().id(2L).build());
        }};

        //when
        when(userRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(User.builder().id(1L).build());
            add(User.builder().id(2L).build());
        }});
        List<UserListDto> actual = userService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldAddUser() throws RepositoryException, ServiceException {
        //given
        HashSet<Role> roles = new HashSet<>() {{add(Role.builder().id(1L).build());}};
        UserSaveDto expected = UserSaveDto.builder().id(3L).rolesId(new ArrayList<>() {{add(1L);}}).build();

        //when
        when(userRepository.add(User.builder().roles(roles).build())).thenReturn(User.builder().id(3L).roles(roles).build());
        UserSaveDto actual = userService.add(UserSaveDto.builder().rolesId(new ArrayList<>() {{add(1L);}}).build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTest_shouldUpdateUser() throws RepositoryException, ServiceException {
        //given
        UserUpdateDto expected = UserUpdateDto.builder().id(4L).firstName("Sergei").lastName("Smirnov").build();

        //when
        when(userRepository.update(User.builder().id(4L).firstName("Sergei").lastName("Smirnov").build())).thenReturn(true);
        boolean actual = userService.update(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteTest_shouldDeleteUser() throws RepositoryException, ServiceException {
        //given
        Long id = 3L;

        //when
        when(userRepository.delete(id)).thenReturn(true);
        boolean actual = userService.delete(id);

        //then
        Assertions.assertTrue(actual);
        Assertions.assertThrows(ServiceException.class, () -> userService.findById(id));
    }
}