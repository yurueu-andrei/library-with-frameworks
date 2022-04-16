package by.library.yurueu.controller;

import by.library.yurueu.dto.GenreDto;
import by.library.yurueu.service.GenreService;
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

@WebMvcTest(GenreController.class)
public class GenreControllerTest {
    @MockBean
    private GenreService genreService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findByIdTest() throws Exception {
        //given
        Long id = 3L;
        GenreDto genre = GenreDto.builder().id(id).genreName("Hello").build();

        //when
        when(genreService.findById(id)).thenReturn(genre);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/genres/3"))
                .andDo(print())
                .andExpect(jsonPath("$.genreName").value("Hello"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void findAllTest() throws Exception {
        //given
        GenreDto genre1 = GenreDto.builder().id(1L).genreName("Hello").build();
        GenreDto genre2 = GenreDto.builder().id(2L).genreName("GoodBye").build();
        List<GenreDto> genres = new ArrayList<>(){{
            add(genre1);
            add(genre2);
        }};

        //when
        when(genreService.findAll()).thenReturn(genres);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/genres"))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].genreName").value("Hello"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].genreName").value("GoodBye"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}