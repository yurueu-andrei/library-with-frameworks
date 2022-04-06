package by.library.yurueu.service.impl;

import by.library.yurueu.dto.BookSaveDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.Genre;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void add_shouldAddBook() throws ServiceException {
        //given
        BookSaveDto expected = BookSaveDto.builder().id(3L).title("hello")
                .genresId(new ArrayList<>(){{add(1L);}})
                .authorsId(new ArrayList<>(){{add(1L);}})
                .build();
        Book bookWithoutId = Book.builder().title("hello")
                .genres(new HashSet<>(){{add(Genre.builder().id(1L).build());}})
                .authors(new HashSet<>(){{add(Author.builder().id(1L).build());}})
                .build();
        Book bookWithId = Book.builder().id(3L).title("hello")
                .genres(new HashSet<>(){{add(Genre.builder().id(1L).build());}})
                .authors(new HashSet<>(){{add(Author.builder().id(1L).build());}})
                .build();

        //when
        when(bookRepository.save(bookWithoutId))
                .thenReturn(bookWithId);
        BookSaveDto actual = bookService.add(BookSaveDto.builder()
                        .genresId(new ArrayList<>(){{add(1L);}})
                        .authorsId(new ArrayList<>(){{add(1L);}})
                        .title("hello")
                .build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete_shouldDeleteBook() throws ServiceException {
        //given
        Long id = 3L;

        //when
        when(bookRepository.findById(id)).thenReturn(Optional.of(Book.builder().id(3L).build()));
        boolean actual = bookService.delete(id);

        //then
        Assertions.assertTrue(actual);
    }
}