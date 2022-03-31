package by.library.yurueu.service.impl;

import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveDto;
import by.library.yurueu.dto.AuthorUpdateDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void findById_shouldReturnAuthorDto() throws ServiceException, RepositoryException {
        //given
        Long id = 1L;
        AuthorDto expected = AuthorDto.builder().id(id).firstName("Alexander")
                .books(new ArrayList<>(){{add(BookCopyListDto.builder().id(3L).build());}}).build();

        //when
        when(authorRepository.findById(id)).thenReturn(Author.builder().id(id).firstName("Alexander").build());
        when(authorRepository.findBooksByAuthorId(id))
                .thenReturn(new HashSet<>(){{
                    add(Book.builder().id(1L)
                        .bookCopies(new HashSet<>(){{add(BookCopy.builder().id(3L)
                                .book(Book.builder().id(1L).build()).build());}}).build());}});
        AuthorDto actual = authorService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldReturnListOfAuthorListDto() throws RepositoryException, ServiceException {
        //given
        List<AuthorListDto> expected = new ArrayList<>() {{
            add(AuthorListDto.builder().id(1L).build());
            add(AuthorListDto.builder().id(2L).build());
        }};

        //when
        when(authorRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(Author.builder().id(1L).build());
            add(Author.builder().id(2L).build());
        }});
        List<AuthorListDto> actual = authorService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void add_shouldAddAuthor() throws RepositoryException, ServiceException {
        //given
        AuthorSaveDto expected = AuthorSaveDto.builder().id(3L).firstName("Alexander").build();

        //when
        when(authorRepository.add(Author.builder().firstName("Alexander").build()))
                .thenReturn(Author.builder().id(3L).firstName("Alexander").build());
        AuthorSaveDto actual = authorService.add(AuthorSaveDto.builder().firstName("Alexander").build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update_shouldUpdateAuthor() throws RepositoryException, ServiceException {
        //given
        AuthorUpdateDto expected = AuthorUpdateDto.builder().id(4L).firstName("Alexander").build();

        //when
        when(authorRepository.update(Author.builder().id(4L).firstName("Alexander").build())).thenReturn(true);
        boolean actual = authorService.update(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteAuthor() throws RepositoryException, ServiceException {
        //given
        Long id = 3L;

        //when
        when(authorRepository.delete(id)).thenReturn(true);
        boolean actual = authorService.delete(id);

        //then
        Assertions.assertTrue(actual);
        Assertions.assertThrows(ServiceException.class, () -> authorService.findById(id));
    }
}