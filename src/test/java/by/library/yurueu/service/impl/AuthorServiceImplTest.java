package by.library.yurueu.service.impl;

import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveAndUpdateDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.mapper.AuthorMapper;
import by.library.yurueu.mapper.BookCopyMapper;
import by.library.yurueu.repository.AuthorRepository;
import by.library.yurueu.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @Spy
    @InjectMocks
    private AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);

    @Spy
    private BookCopyMapper bookCopyMapper = Mappers.getMapper(BookCopyMapper.class);

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void findById_shouldReturnAuthorDto() throws ServiceException {
        //given
        Long id = 1L;
        AuthorDto expected = AuthorDto.builder().id(id).firstName("Alexander")
                .books(new ArrayList<>(){{add(BookCopyListDto.builder().id(3L).build());}}).build();

        //when
        when(authorRepository.findById(id)).thenReturn(Optional.of(Author.builder().id(id)
                .firstName("Alexander")
                .books(new HashSet<>(){{add(Book.builder().bookCopies(
                        new HashSet<>(){{add(BookCopy.builder().id(3L).book(Book.builder().id(3L).build()).build());}})
                        .build());}})
                .build()));
        AuthorDto actual = authorService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldReturnListOfAuthorListDto() throws ServiceException {
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
    void add_shouldAddAuthor() throws ServiceException {
        //given
        AuthorSaveAndUpdateDto expected = AuthorSaveAndUpdateDto.builder().id(3L).firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2003,4,1)).imagePath("path").build();
        Author authorWithoutId = Author.builder().status("ACTIVE").firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2003,4,1)).imagePath("path").build();
        Author authorWithId = Author.builder().id(3L).status("ACTIVE").firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2003,4,1)).imagePath("path").build();

        //when
        when(authorRepository.save(authorWithoutId))
                .thenReturn(authorWithId);
        AuthorSaveAndUpdateDto actual = authorService.add(AuthorSaveAndUpdateDto.builder().firstName("Alexander").lastName("Pushkin")
                .birthDate(LocalDate.of(2003,4,1)).imagePath("path").build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update_shouldUpdateAuthor() throws ServiceException {
        //given
        AuthorSaveAndUpdateDto expected = AuthorSaveAndUpdateDto.builder().id(4L).firstName("Alexander").build();
        Author author = Author.builder().id(4L).firstName("Alexander").build();

        //when
        when(authorRepository.findById(4L)).thenReturn(Optional.of(author));
        boolean actual = authorService.update(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteAuthor() throws ServiceException {
        //given
        Long id = 3L;
        Book book = Book.builder().id(1L).authors(new HashSet<>()).build();
        Set<Book> books = new HashSet<>() {{add(book);}};

        //when
        when(authorRepository.findById(id)).thenReturn(Optional.of(Author.builder().id(3L).books(books).build()));
        when(bookRepository.save(book)).thenReturn(book);
        boolean actual = authorService.delete(id);

        //then
        Assertions.assertTrue(actual);
    }
}