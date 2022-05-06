package by.library.yurueu.controller.impl;

import by.library.yurueu.controller.BaseControllerTest;
import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveAndUpdateDto;
import by.library.yurueu.dto.BookCopyListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthorControllerTest extends BaseControllerTest {
    @Test
    @WithMockUser(authorities = "admin")
    public void findByIdTest_shouldReturnAuthorAndStatus200ForAdmin() throws Exception {
        //given
        Long id = 3L;
        List<BookCopyListDto> books = new ArrayList<>(){{add(BookCopyListDto.builder().id(id).build());}};
        AuthorDto authorDto = AuthorDto.builder().id(id).firstName("Andrei").lastName("Yurueu")
                .birthDate(LocalDate.of(2003,4,1)).imagePath("imagePath").books(books).build();

        //when
        when(authorService.findById(id)).thenReturn(authorDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/authors/3"))
                .andExpect(jsonPath("$.firstName").value("Andrei"))
                .andExpect(jsonPath("$.lastName").value("Yurueu"))
                .andExpect(jsonPath("$.birthDate").value("2003-04-01"))
                .andExpect(jsonPath("$.imagePath").value("imagePath"))
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "admin")
    public void findAllTest_shouldReturnAuthorsAndStatus200ForAdmin() throws Exception {
        //given
        AuthorListDto author1 = AuthorListDto.builder().id(1L).firstName("Alexander").lastName("Pushkin").build();
        AuthorListDto author2 = AuthorListDto.builder().id(2L).firstName("Janka").lastName("Kupala").build();
        List<AuthorListDto> authors = new ArrayList<>(){{
            add(author1);
            add(author2);
        }};

        //when
        when(authorService.findAll()).thenReturn(authors);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Alexander"))
                .andExpect(jsonPath("$[0].lastName").value("Pushkin"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Janka"))
                .andExpect(jsonPath("$[1].lastName").value("Kupala"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "admin")
    public void addTest_shouldReturnAuthorAndStatus200ForAdmin() throws Exception {
        //given
        AuthorSaveAndUpdateDto authorWithoutId = AuthorSaveAndUpdateDto.builder().firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2002,1,3)).imagePath("path").build();
        AuthorSaveAndUpdateDto authorWithId = AuthorSaveAndUpdateDto.builder().id(3L).firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2002,1,3)).imagePath("path").build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(authorService.add(authorWithoutId)).thenReturn(authorWithId);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .content(mapper.writeValueAsString(authorWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Alexander"))
                .andExpect(jsonPath("$.lastName").value("Pushkin"))
                .andExpect(jsonPath("$.birthDate").value("2002-01-03"))
                .andExpect(jsonPath("$.imagePath").value("path"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "admin")
    public void updateTest_shouldReturnAuthorAndStatus200ForAdmin() throws Exception {
        //given
        AuthorSaveAndUpdateDto author = AuthorSaveAndUpdateDto.builder().id(3L).firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2002,1,3)).imagePath("path").build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(authorService.update(author)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/authors")
                        .content(mapper.writeValueAsString(author))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.firstName").value("Alexander"))
                .andExpect(jsonPath("$.lastName").value("Pushkin"))
                .andExpect(jsonPath("$.birthDate").value("2002-01-03"))
                .andExpect(jsonPath("$.imagePath").value("path"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "admin")
    public void deleteTest_shouldReturnTrueAndStatus200ForAdmin() throws Exception {
        //given
        Long id = 3L;

        //when
        when(authorService.delete(id)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/authors/3"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "user")
    public void findByIdTest_shouldReturnStatus200ForUser() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/3"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user")
    public void findAllTest_shouldReturnStatus200ForUser() throws Exception {
        //given & when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "user")
    public void addTest_shouldReturnStatus403ForUser() throws Exception {
        //given
        AuthorSaveAndUpdateDto authorWithoutId = AuthorSaveAndUpdateDto.builder().firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2002,1,3)).imagePath("path").build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .content(mapper.writeValueAsString(authorWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "user")
    public void updateTest_shouldReturnStatus403ForUser() throws Exception {
        //given
        AuthorSaveAndUpdateDto author = AuthorSaveAndUpdateDto.builder().id(3L).firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2002,1,3)).imagePath("path").build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/authors")
                        .content(mapper.writeValueAsString(author))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "user")
    public void deleteTest_shouldReturnTrueAndStatus403ForUser() throws Exception {
        //given & when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/authors/3"))
                .andExpect(status().isForbidden())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void findByIdTest_shouldReturnStatus200ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/3"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllTest_shouldReturnStatus200ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(status().isOk());
    }

    @Test
    public void addTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given
        AuthorSaveAndUpdateDto authorWithoutId = AuthorSaveAndUpdateDto.builder().firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2002,1,3)).imagePath("path").build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .content(mapper.writeValueAsString(authorWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given
        AuthorSaveAndUpdateDto author = AuthorSaveAndUpdateDto.builder().id(3L).firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2002,1,3)).imagePath("path").build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/authors")
                        .content(mapper.writeValueAsString(author))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteTest_shouldReturnTrueAndStatus401ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/3"))
                .andExpect(status().isUnauthorized());
    }
}