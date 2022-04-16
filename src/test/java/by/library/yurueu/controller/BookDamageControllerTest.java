package by.library.yurueu.controller;

import by.library.yurueu.dto.BookDamageDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.service.BookDamageService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookDamageController.class)
public class BookDamageControllerTest {
    @MockBean
    private BookDamageService bookDamageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findByIdTest() throws Exception {
        //given
        Long id = 3L;
        BookDamageDto damage = BookDamageDto.builder().id(id).imagePath("path").damageDescription("description").userId(id).orderId(id).bookCopyId(id).build();

        //when
        when(bookDamageService.findById(id)).thenReturn(damage);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/damages/3"))
                .andDo(print())
                .andExpect(jsonPath("$.imagePath").value("path"))
                .andExpect(jsonPath("$.damageDescription").value("description"))
                .andExpect(jsonPath("$.userId").value(3))
                .andExpect(jsonPath("$.orderId").value(3))
                .andExpect(jsonPath("$.bookCopyId").value(3))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void findAllTest() throws Exception {
        //given
        BookDamageListDto bookDamageListDto1 = BookDamageListDto.builder().id(1L).build();
        BookDamageListDto bookDamageListDto2 = BookDamageListDto.builder().id(2L).build();
        List<BookDamageListDto> damages = new ArrayList<>(){{
            add(bookDamageListDto1);
            add(bookDamageListDto2);
        }};

        //when
        when(bookDamageService.findAll()).thenReturn(damages);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/damages"))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void addTest() throws Exception {
        //given
        Long id = 3L;
        BookDamageDto damageWithoutId = BookDamageDto.builder().imagePath("path").damageDescription("description").userId(id).orderId(id).bookCopyId(id).build();
        BookDamageDto damageWithId = BookDamageDto.builder().id(id).imagePath("path").damageDescription("description").userId(id).orderId(id).bookCopyId(id).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(bookDamageService.add(damageWithoutId)).thenReturn(damageWithId);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/damages")
                        .content(mapper.writeValueAsString(damageWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.imagePath").value("path"))
                .andExpect(jsonPath("$.damageDescription").value("description"))
                .andExpect(jsonPath("$.userId").value(3))
                .andExpect(jsonPath("$.orderId").value(3))
                .andExpect(jsonPath("$.bookCopyId").value(3))
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
        when(bookDamageService.delete(id)).thenReturn(true);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/damages/3"))
                .andDo(print())
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}