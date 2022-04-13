package by.library.yurueu.service.impl;

import by.library.yurueu.dto.GenreListDto;
import by.library.yurueu.dto.GenreSaveDto;
import by.library.yurueu.dto.GenreUpdateDto;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.Genre;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.BookRepository;
import by.library.yurueu.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
class GenreServiceImplTest {
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private GenreServiceImpl genreService;

    @Test
    void findById_shouldReturnGenreDto() throws ServiceException {
        //given
        Long id = 1L;
        GenreListDto expected = GenreListDto.builder().id(id).genreName("hello").build();

        //when
        when(genreRepository.findById(id)).thenReturn(Optional.of(Genre.builder().id(id).genreName("hello").build()));
        GenreListDto actual = genreService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldReturnListOfRoleDto() throws ServiceException {
        //given
        List<GenreListDto> expected = new ArrayList<>() {{
            add(GenreListDto.builder().id(1L).build());
            add(GenreListDto.builder().id(2L).build());
        }};

        //when
        when(genreRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(Genre.builder().id(1L).build());
            add(Genre.builder().id(2L).build());
        }});
        List<GenreListDto> actual = genreService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void add_shouldAddRole() throws ServiceException {
        //given
        GenreSaveDto expected = GenreSaveDto.builder().id(3L).genreName("hello").deleteStatus("EXISTS").build();
        Genre genreWithoutId = Genre.builder().genreName("hello").deleteStatus("EXISTS").build();
        Genre genreWithId = Genre.builder().id(3L).genreName("hello").deleteStatus("EXISTS").build();

        //when
        when(genreRepository.save(genreWithoutId)).thenReturn(genreWithId);
        GenreSaveDto actual = genreService.add(GenreSaveDto.builder().genreName("hello").deleteStatus("EXISTS").build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update_shouldUpdateRole() throws ServiceException {
        //given
        GenreUpdateDto expected = GenreUpdateDto.builder().id(4L).genreName("hello").build();
        Genre genre = Genre.builder().id(4L).genreName("hello").build();

        //when
        when(genreRepository.save(genre)).thenReturn(genre);
        boolean actual = genreService.update(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteRole() throws ServiceException {
        //given
        Long id = 3L;
        Book book = Book.builder().id(1L).genres(new HashSet<>()).build();
        Set<Book> books = new HashSet<>() {{add(book);}};

        //when
        when(genreRepository.findById(id)).thenReturn(Optional.of(Genre.builder().id(3L).books(books).build()));
        when(bookRepository.save(book)).thenReturn(book);
        boolean actual = genreService.delete(id);

        //then
        Assertions.assertTrue(actual);
    }
}