package by.library.yurueu.service.impl;

import by.library.yurueu.dto.GenreDto;
import by.library.yurueu.entity.Genre;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class GenreServiceImplTest {
    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private GenreServiceImpl genreService;

    @Test
    void findById_shouldReturnGenreDto() throws ServiceException {
        //given
        Long id = 1L;
        GenreDto expected = GenreDto.builder().id(id).genreName("hello").build();

        //when
        when(genreRepository.findById(id)).thenReturn(Optional.of(Genre.builder().id(id).genreName("hello").build()));
        GenreDto actual = genreService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldReturnListOfRoleDto() throws ServiceException {
        //given
        List<GenreDto> expected = new ArrayList<>() {{
            add(GenreDto.builder().id(1L).build());
            add(GenreDto.builder().id(2L).build());
        }};

        //when
        when(genreRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(Genre.builder().id(1L).build());
            add(Genre.builder().id(2L).build());
        }});
        List<GenreDto> actual = genreService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }
}