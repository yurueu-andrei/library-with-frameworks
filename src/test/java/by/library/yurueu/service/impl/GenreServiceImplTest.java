package by.library.yurueu.service.impl;

import by.library.yurueu.dto.GenreListDto;
import by.library.yurueu.dto.GenreUpdateDto;
import by.library.yurueu.entity.Genre;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.GenreRepository;
import by.library.yurueu.repository.impl.GenreRepositoryImpl;
import by.library.yurueu.service.GenreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    private final GenreRepository genreRepository;
    private final GenreService genreService;

    public GenreServiceImplTest() {
        genreRepository = mock(GenreRepositoryImpl.class);
        genreService = new GenreServiceImpl(genreRepository);
    }

    @Test
    void findById_shouldReturnGenreDto() throws ServiceException, RepositoryException {
        //given
        Long id = 1L;
        GenreListDto expected = GenreListDto.builder().id(id).genreName("hello").build();

        //when
        when(genreRepository.findById(id)).thenReturn(Genre.builder().id(id).genreName("hello").build());
        GenreListDto actual = genreService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldReturnListOfRoleDto() throws RepositoryException, ServiceException {
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
    void add_shouldAddRole() throws RepositoryException, ServiceException {
        //given
        GenreListDto expected = GenreListDto.builder().id(3L).genreName("hello").build();

        //when
        when(genreRepository.add(Genre.builder().genreName("hello").build()))
                .thenReturn(Genre.builder().id(3L).genreName("hello").build());
        GenreListDto actual = genreService.add(GenreListDto.builder().genreName("hello").build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update_shouldUpdateRole() throws RepositoryException, ServiceException {
        //given
        GenreUpdateDto expected = GenreUpdateDto.builder().id(4L).genreName("hello").build();

        //when
        when(genreRepository.update(Genre.builder().id(4L).genreName("hello").build())).thenReturn(true);
        boolean actual = genreService.update(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteRole() throws RepositoryException, ServiceException {
        //given
        Long id = 3L;

        //when
        when(genreRepository.delete(id)).thenReturn(true);
        boolean actual = genreService.delete(id);

        //then
        Assertions.assertTrue(actual);
        Assertions.assertThrows(ServiceException.class, () -> genreService.findById(id));
    }
}