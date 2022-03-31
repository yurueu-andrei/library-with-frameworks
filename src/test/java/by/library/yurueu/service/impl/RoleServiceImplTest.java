package by.library.yurueu.service.impl;

import by.library.yurueu.dto.RoleDto;
import by.library.yurueu.entity.Role;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void findAll_shouldReturnListOfRoleDto() throws RepositoryException, ServiceException {
        //given
        List<RoleDto> expected = new ArrayList<>() {{
            add(RoleDto.builder().id(1L).build());
            add(RoleDto.builder().id(2L).build());
        }};

        //when
        when(roleRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(Role.builder().id(1L).build());
            add(Role.builder().id(2L).build());
        }});
        List<RoleDto> actual = roleService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }
}