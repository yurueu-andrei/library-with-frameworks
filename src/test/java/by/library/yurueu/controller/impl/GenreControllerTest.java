package by.library.yurueu.controller.impl;

import by.library.yurueu.controller.BaseControllerTest;
import by.library.yurueu.dto.GenreDto;
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

public class GenreControllerTest extends BaseControllerTest {
    @Test
    @WithMockUser(authorities = "admin")
    public void findByIdTest_shouldReturnGenreAndStatus200ForAdmin() throws Exception {
        //given
        Long id = 3L;
        GenreDto genre = GenreDto.builder().id(id).genreName("Hello").build();

        //when
        when(genreService.findById(id)).thenReturn(genre);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/genres/3"))
                .andExpect(jsonPath("$.genreName").value("Hello"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "admin")
    public void findAllTest_shouldReturnGenreAndStatus200ForAdmin() throws Exception {
        //given
        GenreDto genre1 = GenreDto.builder().id(1L).genreName("Hello").build();
        GenreDto genre2 = GenreDto.builder().id(2L).genreName("GoodBye").build();
        List<GenreDto> genres = new ArrayList<>(){{
            add(genre1);
            add(genre2);
        }};

        //when
        when(genreService.findAll()).thenReturn(genres);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/genres"))
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

    @Test
    @WithMockUser(authorities = "user")
    public void findByIdTest_shouldReturnStatus200ForUser() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/genres/3"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user")
    public void findAllTest_shouldReturnStatus200ForUser() throws Exception {
        //given & when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/genres"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void findByIdTest_shouldReturnStatus200ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/genres/3"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllTest_shouldReturnStatus200ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/genres"))
                .andExpect(status().isOk());
    }
}