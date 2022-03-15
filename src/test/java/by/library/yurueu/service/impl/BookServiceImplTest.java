package by.library.yurueu.service.impl;

import by.library.yurueu.dto.BookSaveDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.Genre;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.BookRepository;
import by.library.yurueu.repository.impl.BookRepositoryImpl;
import by.library.yurueu.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    private final BookRepository bookRepository;
    private final BookService bookService;

    public BookServiceImplTest() {
        bookRepository = mock(BookRepositoryImpl.class);
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    void add_shouldAddBook() throws RepositoryException, ServiceException {
        //given
        BookSaveDto expected = BookSaveDto.builder().id(3L).title("hello")
                .genresId(new ArrayList<>(){{add(1L);}})
                .authorsId(new ArrayList<>(){{add(1L);}})
                .build();

        //when
        when(bookRepository.findGenresByGenresId(new HashSet<>(){{add(1L);}}))
                .thenReturn(new HashSet<>(){{add(Genre.builder().id(1L).build());}});
        when(bookRepository.findAuthorsByAuthorsId(new HashSet<>(){{add(1L);}}))
                .thenReturn(new HashSet<>(){{add(Author.builder().id(1L).build());}});

        when(bookRepository.add(Book.builder().title("hello").build()))
                .thenReturn(Book.builder().id(3L).title("hello")
                        .genres(new HashSet<>(){{add(Genre.builder().id(1L).build());}})
                        .authors(new HashSet<>(){{add(Author.builder().id(1L).build());}})
                        .build());
        BookSaveDto actual = bookService.add(BookSaveDto.builder()
                        .genresId(new ArrayList<>(){{add(1L);}})
                        .authorsId(new ArrayList<>(){{add(1L);}})
                        .title("hello")
                .build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete_shouldDeleteBook() throws RepositoryException, ServiceException {
        //given
        Long id = 3L;

        //when
        when(bookRepository.delete(id)).thenReturn(true);
        boolean actual = bookService.delete(id);

        //then
        Assertions.assertTrue(actual);
    }
}