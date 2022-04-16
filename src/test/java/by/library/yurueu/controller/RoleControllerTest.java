package by.library.yurueu.controller;

import by.library.yurueu.dto.RoleDto;
import by.library.yurueu.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleController.class)
public class RoleControllerTest {
    @MockBean
    private RoleService roleService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findAllTest() throws Exception {
        //given
        RoleDto role1 = RoleDto.builder().id(1L).roleName("Hello").build();
        RoleDto role2 = RoleDto.builder().id(2L).roleName("GoodBye").build();
        List<RoleDto> authors = new ArrayList<>(){{
            add(role1);
            add(role2);
        }};

        //when
        when(roleService.findAll()).thenReturn(authors);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/roles"))
                .andDo(print())
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
}