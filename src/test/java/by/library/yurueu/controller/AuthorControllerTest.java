package by.library.yurueu.controller;

import by.library.yurueu.dto.AuthorSaveAndUpdateDto;
import by.library.yurueu.service.AuthorService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {
    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "AUTHOR_WRITE")
    public void addTest_shouldReturnAuthorAndStatus200ForForUserWithAuthorWriteAuthority() throws Exception {
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
    @WithMockUser(authorities = "AUTHOR_WRITE")
    public void updateTest_shouldReturnAuthorAndStatus200ForForUserWithAuthorWriteAuthority() throws Exception {
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
    @WithMockUser(authorities = "AUTHOR_DELETE")
    public void deleteTest_shouldReturnTrueAndStatus200ForUserWithAuthorDeleteAuthority() throws Exception {
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