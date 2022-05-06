package by.library.yurueu.controller.impl;

import by.library.yurueu.controller.BaseControllerTest;
import by.library.yurueu.dto.RoleDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoleControllerTest extends BaseControllerTest {
    @Test
    @WithMockUser(authorities = "ROLE_READ")
    public void findAllTest_shouldReturnRolesAndStatus200ForUserWithRoleReadAuthority() throws Exception {
        //given
        RoleDto role1 = RoleDto.builder().id(1L).roleName("Hello").build();
        RoleDto role2 = RoleDto.builder().id(2L).roleName("GoodBye").build();
        List<RoleDto> authors = new ArrayList<>(){{
            add(role1);
            add(role2);
        }};

        //when
        when(roleService.findAll()).thenReturn(authors);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/roles"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].roleName").value("Hello"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].roleName").value("GoodBye"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void findAllTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/roles"))
                .andExpect(status().isUnauthorized());
    }
}