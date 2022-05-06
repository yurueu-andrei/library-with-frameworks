package by.library.yurueu.controller.impl;

import by.library.yurueu.controller.BaseControllerTest;
import by.library.yurueu.dto.BookDamageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookDamageControllerTest extends BaseControllerTest {
    @Test
    @WithMockUser(authorities = "BOOK_DAMAGE_WRITE")
    public void addTest_shouldReturnDamageAndStatus200ForUserWithBookDamageWriteAuthority() throws Exception {
        //given
        Long id = 3L;
        BookDamageDto damageWithoutId = BookDamageDto.builder().imagePath("path").damageDescription("description").userId(id).orderId(id).bookCopyId(id).build();
        BookDamageDto damageWithId = BookDamageDto.builder().id(id).imagePath("path").damageDescription("description").userId(id).orderId(id).bookCopyId(id).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(bookDamageService.add(damageWithoutId)).thenReturn(damageWithId);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/damages")
                        .content(mapper.writeValueAsString(damageWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
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
    @WithMockUser(authorities = "BOOK_DAMAGE_DELETE")
    public void deleteTest_shouldReturnTrueAndStatus200ForUserWithBookDamageDeleteAuthority() throws Exception {
        //given
        Long id = 3L;

        //when
        when(bookDamageService.delete(id)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/damages/3"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void findByIdTest_shouldReturnStatus200ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/damages/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllTest_shouldReturnStatus200ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/damages"))
                .andExpect(status().isOk());
    }

    @Test
    public void addTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given
        Long id = 3L;
        BookDamageDto damageWithoutId = BookDamageDto.builder().imagePath("path").damageDescription("description").userId(id).orderId(id).bookCopyId(id).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/damages")
                        .content(mapper.writeValueAsString(damageWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/damages/3"))
                .andExpect(status().isUnauthorized());
    }
}