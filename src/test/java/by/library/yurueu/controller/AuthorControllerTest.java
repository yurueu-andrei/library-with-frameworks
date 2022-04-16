package by.library.yurueu.controller;

import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveAndUpdateDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {
    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findByIdTest() throws Exception {
        //given
        Long id = 3L;
        List<BookCopyListDto> books = new ArrayList<>(){{add(BookCopyListDto.builder().id(id).build());}};
        AuthorDto authorDto = AuthorDto.builder().id(id).firstName("Andrei").lastName("Yurueu")
                .birthDate(LocalDate.of(2003,4,1)).imagePath("imagePath").books(books).build();

        //when
        when(authorService.findById(id)).thenReturn(authorDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/authors/3"))
                .andDo(print())
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
    public void findAllTest() throws Exception {
        //given
        AuthorListDto author1 = AuthorListDto.builder().id(1L).firstName("Alexander").lastName("Pushkin").build();
        AuthorListDto author2 = AuthorListDto.builder().id(2L).firstName("Janka").lastName("Kupala").build();
        List<AuthorListDto> authors = new ArrayList<>(){{
            add(author1);
            add(author2);
        }};

        //when
        when(authorService.findAll()).thenReturn(authors);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andDo(print())
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
    public void addTest() throws Exception {
        //given
        AuthorSaveAndUpdateDto authorWithoutId = AuthorSaveAndUpdateDto.builder().firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2002,1,3)).imagePath("path").build();
        AuthorSaveAndUpdateDto authorWithId = AuthorSaveAndUpdateDto.builder().id(3L).firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2002,1,3)).imagePath("path").build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(authorService.add(authorWithoutId)).thenReturn(authorWithId);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .content(mapper.writeValueAsString(authorWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
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
    public void updateTest() throws Exception {
        //given
        AuthorSaveAndUpdateDto author = AuthorSaveAndUpdateDto.builder().id(3L).firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2002,1,3)).imagePath("path").build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(authorService.update(author)).thenReturn(true);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/authors")
                        .content(mapper.writeValueAsString(author))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
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
    public void deleteTest() throws Exception {
        //given
        Long id = 3L;

        //when
        when(authorService.delete(id)).thenReturn(true);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/authors/3"))
                .andDo(print())
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}