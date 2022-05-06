package by.library.yurueu.controller.impl;

import by.library.yurueu.controller.BaseControllerTest;
import by.library.yurueu.dto.BookCopySaveAndUpdateDto;
import by.library.yurueu.dto.BookSaveDto;
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

public class BookCopyControllerTest extends BaseControllerTest {
    @Test
    @WithMockUser(authorities = "BOOK_COPY_WRITE")
    public void addBookCopyTest_shouldReturnBookCopyAndStatus200ForUserWithBookCopyWriteAuthority() throws Exception {
        //given
        BookCopySaveAndUpdateDto bookCopyWithoutId = BookCopySaveAndUpdateDto.builder().status("AVAILABLE").registrationDate(LocalDate.of(2003,1,4)).pricePerDay(123).imagePath("path").bookId(2L).build();
        BookCopySaveAndUpdateDto bookCopyWithId = BookCopySaveAndUpdateDto.builder().id(3L).status("AVAILABLE").registrationDate(LocalDate.of(2003,1,4)).pricePerDay(123).imagePath("path").bookId(2L).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(bookCopyService.add(bookCopyWithoutId)).thenReturn(bookCopyWithId);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/books/copies")
                        .content(mapper.writeValueAsString(bookCopyWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("AVAILABLE"))
                .andExpect(jsonPath("$.registrationDate").value("2003-01-04"))
                .andExpect(jsonPath("$.pricePerDay").value(123))
                .andExpect(jsonPath("$.imagePath").value("path"))
                .andExpect(jsonPath("$.bookId").value(2))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "BOOK_WRITE")
    public void addBookTest_shouldReturnBookAndStatus200ForUserWithBookWriteAuthority() throws Exception {
        //given
        List<Long> authorsId = new ArrayList<>(){{add(1L);}};
        List<Long> genresId = new ArrayList<>(){{add(2L);}};
        BookSaveDto bookWithoutId = BookSaveDto.builder().title("Hello").pagesNumber(123).imagePath("path").authorsId(authorsId).genresId(genresId).build();
        BookSaveDto bookWithId = BookSaveDto.builder().id(3L).title("Hello").pagesNumber(123).imagePath("path").authorsId(authorsId).genresId(genresId).build();

        ObjectMapper mapper = new ObjectMapper();
        //when
        when(bookService.add(bookWithoutId)).thenReturn(bookWithId);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .content(mapper.writeValueAsString(bookWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Hello"))
                .andExpect(jsonPath("$.pagesNumber").value(123))
                .andExpect(jsonPath("$.imagePath").value("path"))
                .andExpect(jsonPath("$.authorsId").isArray())
                .andExpect(jsonPath("$.genresId").isArray())
                .andExpect(jsonPath("$.authorsId[0]").value(1))
                .andExpect(jsonPath("$.genresId[0]").value(2))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "BOOK_COPY_WRITE")
    public void updateTest_shouldReturnBookCopyAndStatus200ForUserWithBookCopyWriteAuthority() throws Exception {
        //given
        BookCopySaveAndUpdateDto bookCopy = BookCopySaveAndUpdateDto.builder().id(3L).status("AVAILABLE").registrationDate(LocalDate.of(2003,1,4)).pricePerDay(123).imagePath("path").bookId(2L).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(bookCopyService.update(bookCopy)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/books")
                        .content(mapper.writeValueAsString(bookCopy))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("AVAILABLE"))
                .andExpect(jsonPath("$.registrationDate").value("2003-01-04"))
                .andExpect(jsonPath("$.pricePerDay").value(123))
                .andExpect(jsonPath("$.imagePath").value("path"))
                .andExpect(jsonPath("$.bookId").value(2))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
    @Test
    @WithMockUser(authorities = "BOOK_COPY_DELETE")
    public void deleteBookCopyTest_shouldReturnTrueAndStatus200ForUserWithBookCopyDeleteAuthority() throws Exception {
        //given
        Long id = 3L;

        //when
        when(bookCopyService.delete(id)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/books/copies/3"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "BOOK_DELETE")
    public void deleteBookTest_shouldReturnTrueAndStatus200ForUserWithBookDeleteAuthority() throws Exception {
        //given
        Long id = 3L;

        //when
        when(bookService.delete(id)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/books/3"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void findByIdTest_shouldReturnStatus200ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/books/3"))
                .andExpect(status().isOk());
    }
    @Test
    public void findAllTest_shouldReturnStatus200ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk());
    }

    @Test
    public void addBookCopyTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given
        BookCopySaveAndUpdateDto bookCopyWithoutId = BookCopySaveAndUpdateDto.builder().status("AVAILABLE").registrationDate(LocalDate.of(2003,1,4)).pricePerDay(123).imagePath("path").bookId(2L).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/books/copies")
                        .content(mapper.writeValueAsString(bookCopyWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addBookTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given
        List<Long> authorsId = new ArrayList<>(){{add(1L);}};
        List<Long> genresId = new ArrayList<>(){{add(2L);}};
        BookSaveDto bookWithoutId = BookSaveDto.builder().title("Hello").pagesNumber(123).imagePath("path").authorsId(authorsId).genresId(genresId).build();

        ObjectMapper mapper = new ObjectMapper();
        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .content(mapper.writeValueAsString(bookWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given
        BookCopySaveAndUpdateDto bookCopy = BookCopySaveAndUpdateDto.builder().id(3L).status("AVAILABLE").registrationDate(LocalDate.of(2003,1,4)).pricePerDay(123).imagePath("path").bookId(2L).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/books")
                        .content(mapper.writeValueAsString(bookCopy))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void deleteBookCopyTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/copies/3"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteBookTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/3"))
                .andExpect(status().isUnauthorized());
    }
}