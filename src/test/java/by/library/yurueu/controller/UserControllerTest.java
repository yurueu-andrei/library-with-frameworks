package by.library.yurueu.controller;

import by.library.yurueu.dto.OrderListDto;
import by.library.yurueu.dto.UserDto;
import by.library.yurueu.dto.UserListDto;
import by.library.yurueu.dto.UserSaveDto;
import by.library.yurueu.dto.UserUpdateDto;
import by.library.yurueu.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "USER_READ")
    public void findByIdTest_shouldReturnUserAndStatus200ForAdmin() throws Exception {
        //given
        Long id = 3L;
        List<OrderListDto> orders = new ArrayList<>(){{add(OrderListDto.builder().id(id).build());}};
        List<Long> rolesId = new ArrayList<>(){{add(1L);}};
        UserDto user = UserDto.builder().id(id).firstName("Andrei").lastName("Yurueu").passportNumber("123").email("email")
                .address("Churlenisa").birthDate(LocalDate.of(2003,4,1)).orders(orders).rolesId(rolesId)
                .build();

        //when
        when(userService.findById(id)).thenReturn(user);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/3"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.firstName").value("Andrei"))
                .andExpect(jsonPath("$.lastName").value("Yurueu"))
                .andExpect(jsonPath("$.passportNumber").value("123"))
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.address").value("Churlenisa"))
                .andExpect(jsonPath("$.birthDate").value("2003-04-01"))
                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.rolesId").isArray())
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "USER_READ")
    public void findAllTest_shouldReturnUsersAndStatus200ForAdmin() throws Exception {
        //given
        UserListDto user1 = UserListDto.builder().id(1L).email("email").address("Churlenisa").build();
        UserListDto user2 = UserListDto.builder().id(2L).email("email").address("Churlenisa").build();
        List<UserListDto> users = new ArrayList<>(){{
            add(user1);
            add(user2);
        }};

        //when
        when(userService.findAll()).thenReturn(users);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("email"))
                .andExpect(jsonPath("$[0].address").value("Churlenisa"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].email").value("email"))
                .andExpect(jsonPath("$[1].address").value("Churlenisa"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "USER_WRITE")
    public void updateTest_shouldReturnUserAndStatus200ForAdmin() throws Exception {
        //given
        UserUpdateDto user = UserUpdateDto.builder().id(3L).firstName("Andrei").lastName("Yurueu").passportNumber("123").email("email")
                .address("Churlenisa").birthDate(LocalDate.of(2003,4,1)).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(userService.update(user)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.firstName").value("Andrei"))
                .andExpect(jsonPath("$.lastName").value("Yurueu"))
                .andExpect(jsonPath("$.passportNumber").value("123"))
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.address").value("Churlenisa"))
                .andExpect(jsonPath("$.birthDate").value("2003-04-01"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "USER_DELETE")
    public void deleteTest_shouldReturnTrueAndStatus200ForAdmin() throws Exception {
        //given
        Long id = 3L;

        //when
        when(userService.delete(id)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/users/3"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void findByIdTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/3"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void findAllTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addTest_shouldReturnStatus200ForUnauthorized() throws Exception {
        //given
        List<Long> rolesId = new ArrayList<>(){{add(1L);}};

        UserSaveDto userWithoutId = UserSaveDto.builder().firstName("Andrei").lastName("Yurueu").passportNumber("123").email("email")
                .address("Churlenisa").birthDate(LocalDate.of(2003,4,1)).rolesId(rolesId).build();
        UserSaveDto userWithId = UserSaveDto.builder().id(3L).firstName("Andrei").lastName("Yurueu").passportNumber("123").email("email")
                .address("Churlenisa").birthDate(LocalDate.of(2003,4,1)).rolesId(rolesId).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(userService.add(userWithoutId)).thenReturn(userWithId);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(mapper.writeValueAsString(userWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void updateTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given
        UserUpdateDto user = UserUpdateDto.builder().id(3L).firstName("Andrei").lastName("Yurueu").passportNumber("123").email("email")
                .address("Churlenisa").birthDate(LocalDate.of(2003,4,1)).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void deleteTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/3"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
}