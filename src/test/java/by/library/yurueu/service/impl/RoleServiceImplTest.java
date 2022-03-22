package by.library.yurueu.service.impl;

import by.library.yurueu.dto.RoleDto;
import by.library.yurueu.entity.Role;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.RoleRepository;
import by.library.yurueu.repository.impl.RoleRepositoryImpl;
import by.library.yurueu.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    private final RoleRepository roleRepository;
    private final RoleService roleService;

    public RoleServiceImplTest() {
        roleRepository = mock(RoleRepositoryImpl.class);
        roleService = new RoleServiceImpl(roleRepository);
    }

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