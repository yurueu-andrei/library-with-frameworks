package by.library.yurueu.controller;

import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.BookCopyDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookCopySaveAndUpdateDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.BookSaveDto;
import by.library.yurueu.dto.GenreDto;
import by.library.yurueu.service.BookCopyService;
import by.library.yurueu.service.BookService;
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

@WebMvcTest(BookCopyController.class)
public class BookCopyControllerTest {
    @MockBean
    private BookCopyService bookCopyService;
    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findByIdTest() throws Exception {
        //given
        Long id = 3L;
        List<AuthorListDto> authors = new ArrayList<>(){{add(AuthorListDto.builder().id(id).build());}};
        List<GenreDto> genres = new ArrayList<>(){{add(GenreDto.builder().id(id).build());}};
        List<BookDamageListDto> damages = new ArrayList<>(){{add(BookDamageListDto.builder().id(id).build());}};
        BookCopyDto bookCopyDto = BookCopyDto.builder().id(id).title("Hello").pagesNumber(234).status("AVAILABLE").registrationDate(LocalDate.of(2002,3,5)).pricePerDay(123).imagePath("path")
                .authors(authors)
                .genres(genres)
                .bookDamages(damages)
                .build();

        //when
        when(bookCopyService.findById(id)).thenReturn(bookCopyDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/books/3"))
                .andDo(print())
                .andExpect(jsonPath("$.title").value("Hello"))
                .andExpect(jsonPath("$.pagesNumber").value(234))
                .andExpect(jsonPath("$.status").value("AVAILABLE"))
                .andExpect(jsonPath("$.registrationDate").value("2002-03-05"))
                .andExpect(jsonPath("$.pricePerDay").value(123))
                .andExpect(jsonPath("$.imagePath").value("path"))
                .andExpect(jsonPath("$.authors").isArray())
                .andExpect(jsonPath("$.genres").isArray())
                .andExpect(jsonPath("$.bookDamages").isArray())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
    @Test
    public void findAllTest() throws Exception {
        //given
        BookCopyListDto book1 = BookCopyListDto.builder().id(1L).title("Hello").pricePerDay(123).imagePath("path1").build();
        BookCopyListDto book2 = BookCopyListDto.builder().id(2L).title("GoodBye").pricePerDay(345).imagePath("path2").build();
        List<BookCopyListDto> books = new ArrayList<>(){{
            add(book1);
            add(book2);
        }};

        //when
        when(bookCopyService.findAll()).thenReturn(books);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Hello"))
                .andExpect(jsonPath("$[0].pricePerDay").value(123))
                .andExpect(jsonPath("$[0].imagePath").value("path1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("GoodBye"))
                .andExpect(jsonPath("$[1].pricePerDay").value(345))
                .andExpect(jsonPath("$[1].imagePath").value("path2"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void addBookCopyTest() throws Exception {
        //given
        BookCopySaveAndUpdateDto bookCopyWithoutId = BookCopySaveAndUpdateDto.builder().status("AVAILABLE").registrationDate(LocalDate.of(2003,1,4)).pricePerDay(123).imagePath("path").bookId(2L).build();
        BookCopySaveAndUpdateDto bookCopyWithId = BookCopySaveAndUpdateDto.builder().id(3L).status("AVAILABLE").registrationDate(LocalDate.of(2003,1,4)).pricePerDay(123).imagePath("path").bookId(2L).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(bookCopyService.add(bookCopyWithoutId)).thenReturn(bookCopyWithId);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/books/copies")
                        .content(mapper.writeValueAsString(bookCopyWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
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
    public void addBookTest() throws Exception {
        //given
        List<Long> authorsId = new ArrayList<>(){{add(1L);}};
        List<Long> genresId = new ArrayList<>(){{add(2L);}};
        BookSaveDto bookWithoutId = BookSaveDto.builder().title("Hello").pagesNumber(123).imagePath("path").authorsId(authorsId).genresId(genresId).build();
        BookSaveDto bookWithId = BookSaveDto.builder().id(3L).title("Hello").pagesNumber(123).imagePath("path").authorsId(authorsId).genresId(genresId).build();

        ObjectMapper mapper = new ObjectMapper();
        //when
        when(bookService.add(bookWithoutId)).thenReturn(bookWithId);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .content(mapper.writeValueAsString(bookWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
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
    public void updateTest() throws Exception {
        //given
        BookCopySaveAndUpdateDto bookCopy = BookCopySaveAndUpdateDto.builder().id(3L).status("AVAILABLE").registrationDate(LocalDate.of(2003,1,4)).pricePerDay(123).imagePath("path").bookId(2L).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(bookCopyService.update(bookCopy)).thenReturn(true);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/books")
                        .content(mapper.writeValueAsString(bookCopy))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
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
    public void deleteBookCopyTest() throws Exception {
        //given
        Long id = 3L;

        //when
        when(bookCopyService.delete(id)).thenReturn(true);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/books/copies/3"))
                .andDo(print())
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void deleteBookTest() throws Exception {
        //given
        Long id = 3L;

        //when
        when(bookService.delete(id)).thenReturn(true);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/books/3"))
                .andDo(print())
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}