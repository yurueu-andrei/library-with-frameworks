package by.library.yurueu.service.impl;

import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.BookCopyDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookCopySaveDto;
import by.library.yurueu.dto.BookCopyUpdateDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.GenreListDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.entity.Genre;
import by.library.yurueu.entity.Order;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.BookCopyRepository;
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
class BookCopyServiceImplTest {
    @Mock
    private BookCopyRepository bookCopyRepository;
    @InjectMocks
    private BookCopyServiceImpl bookCopyService;

    @Test
    void findById_shouldReturnBookCopyDto() throws RepositoryException, ServiceException {
        //given
        Long id = 1L;
        BookCopyDto expected = BookCopyDto.builder().id(id)
                .bookDamages(new ArrayList<>(){{add(BookDamageListDto.builder().id(1L).build());}})
                .authors(new ArrayList<>(){{add(AuthorListDto.builder().id(1L).build());}})
                .genres(new ArrayList<>(){{add(GenreListDto.builder().id(1L).build());}}).build();

        //when
        when(bookCopyRepository.findById(id)).thenReturn(BookCopy.builder().id(id).build());
        when(bookCopyRepository.findBookByBookCopyId(id))
                .thenReturn(Book.builder().id(id)
                        .bookCopies(new HashSet<>(){{add(BookCopy.builder().id(id).build());}})
                        .authors(new HashSet<>(){{add(Author.builder().id(1L).books(new HashSet<>(){{add(Book.builder().id(id).build());}}).build());}})
                        .genres(new HashSet<>(){{add(Genre.builder().id(1L).books(new HashSet<>(){{add(Book.builder().id(id).build());}}).build());}})
                        .build());
        when(bookCopyRepository.findBookDamagesByBookCopyId(id)).thenReturn(new HashSet<>(){{add(BookDamage.builder().id(1L).build());}});
        when(bookCopyRepository.findOrdersByBookCopyId(id)).thenReturn(new HashSet<>(){{add(Order.builder().id(1L).build());}});
        BookCopyDto actual = bookCopyService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldReturnListOfBookCopyListDto() throws RepositoryException, ServiceException {
        //given
        List<BookCopyListDto> expected = new ArrayList<>() {{
            add(BookCopyListDto.builder().id(1L).build());
            add(BookCopyListDto.builder().id(2L).build());
        }};

        //when
        when(bookCopyRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(BookCopy.builder().id(1L).book(Book.builder().id(1L).build()).build());
            add(BookCopy.builder().id(2L).book(Book.builder().id(1L).build()).build());
        }});
        List<BookCopyListDto> actual = bookCopyService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void add_shouldAddBookCopy() throws RepositoryException, ServiceException {
        //given
        BookCopySaveDto expected = BookCopySaveDto.builder().id(3L).bookId(1L).build();

        //when
        when(bookCopyRepository.add(BookCopy.builder().book(Book.builder().id(1L).build()).build()))
                .thenReturn(BookCopy.builder().id(3L).book(Book.builder().id(1L).build()).build());
        BookCopySaveDto actual = bookCopyService.add(BookCopySaveDto.builder().build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update_shouldUpdateBookCopy() throws ServiceException, RepositoryException {
        //given
        BookCopyUpdateDto expected = BookCopyUpdateDto.builder().id(4L).imagePath("image path").build();

        //when
        when(bookCopyRepository.update(BookCopy.builder().id(4L).imagePath("image path").build())).thenReturn(true);
        boolean actual = bookCopyService.update(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteBookCopy() throws RepositoryException, ServiceException {
        //given
        Long id = 3L;

        //when
        when(bookCopyRepository.delete(id)).thenReturn(true);
        boolean actual = bookCopyService.delete(id);

        //then
        Assertions.assertTrue(actual);
        Assertions.assertThrows(ServiceException.class, () -> bookCopyService.findById(id));
    }
}