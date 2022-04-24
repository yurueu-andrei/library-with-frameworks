package by.library.yurueu.service.impl;

import by.library.yurueu.dto.UserDto;
import by.library.yurueu.dto.UserListDto;
import by.library.yurueu.dto.UserSaveDto;
import by.library.yurueu.dto.UserUpdateDto;
import by.library.yurueu.entity.Role;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findByIdTest_shouldReturnUserDto() throws ServiceException {
        //given
        Long id = 1L;
        UserDto expected = UserDto.builder().id(id).rolesId(new ArrayList<>()).orders(new ArrayList<>()).build();

        //when
        when(userRepository.findById(id)).thenReturn(Optional.of(User.builder().id(id)
                .roles(new HashSet<>()).orders(new HashSet<>()).build()));
        UserDto actual = userService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfUserListDto() throws ServiceException {
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
    void addTest_shouldAddUser() throws ServiceException {
        //given
        HashSet<Role> roles = new HashSet<>() {{add(Role.builder().id(1L).build());}};
        UserSaveDto expected = UserSaveDto.builder().id(3L).rolesId(new ArrayList<>() {{add(1L);}}).build();
        User userWithoutId = User.builder().status("ACTIVE").roles(roles).build();
        User userWithId = User.builder().id(3L).status("ACTIVE").roles(roles).build();

        //when
        when(userRepository.save(userWithoutId)).thenReturn(userWithId);
        UserSaveDto actual = userService.add(UserSaveDto.builder().rolesId(new ArrayList<>() {{add(1L);}}).build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTest_shouldUpdateUser() throws ServiceException {
        //given
        UserUpdateDto expected = UserUpdateDto.builder().id(4L).firstName("Sergei").lastName("Smirnov").build();
        User user = User.builder().id(4L).firstName("Sergei").lastName("Smirnov").build();

        //when
        when(userRepository.save(user)).thenReturn(user);
        boolean actual = userService.update(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteTest_shouldDeleteUser() throws ServiceException {
        //given
        Long id = 3L;
        User user = User.builder().id(id).orders(new HashSet<>()).bookDamages(new HashSet<>()).build();

        //when
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        boolean actual = userService.delete(id);

        //then
        Assertions.assertTrue(actual);
    }
}