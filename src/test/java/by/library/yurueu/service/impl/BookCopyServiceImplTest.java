package by.library.yurueu.service.impl;

import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.BookCopyDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookCopySaveAndUpdateDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.GenreDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.entity.Genre;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.mapper.BookCopyMapper;
import by.library.yurueu.repository.BookCopyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class BookCopyServiceImplTest {
    @Mock
    private BookCopyRepository bookCopyRepository;
    @Spy
    private BookCopyMapper bookCopyMapper = Mappers.getMapper(BookCopyMapper.class);
    @InjectMocks
    private BookCopyServiceImpl bookCopyService;

    @Test
    void findById_shouldReturnBookCopyDto() throws ServiceException {
        //given
        Long id = 1L;
        BookCopyDto expected = BookCopyDto.builder().id(id)
                .bookDamages(new ArrayList<>(){{add(BookDamageListDto.builder().id(1L).build());}})
                .authors(new ArrayList<>(){{add(AuthorListDto.builder().id(1L).build());}})
                .genres(new ArrayList<>(){{add(GenreDto.builder().id(1L).build());}}).build();
        //when
        when(bookCopyRepository.findById(id)).thenReturn(Optional.of(BookCopy.builder()
                .id(id)
                .bookDamages(new HashSet<>(){{add(BookDamage.builder().id(1L).build());}})
                .book(Book.builder()
                        .authors(new HashSet<>(){{add(Author.builder().id(1L).build());}})
                        .genres(new HashSet<>(){{add(Genre.builder().id(1L).build());}})
                        .build())
                .build()));
        BookCopyDto actual = bookCopyService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldReturnListOfBookCopyListDto() throws ServiceException {
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
    void add_shouldAddBookCopy() throws ServiceException {
        //given
        BookCopySaveAndUpdateDto expected = BookCopySaveAndUpdateDto.builder().id(3L).status("AVAILABLE").bookId(1L).build();
        BookCopy bookCopyWithoutId = BookCopy.builder().status("AVAILABLE").book(Book.builder().id(1L).build()).build();
        BookCopy bookCopyWithId = BookCopy.builder().id(3L).status("AVAILABLE").book(Book.builder().id(1L).build()).build();
        //when
        when(bookCopyRepository.save(bookCopyWithoutId))
                .thenReturn(bookCopyWithId);
        BookCopySaveAndUpdateDto actual = bookCopyService.add(BookCopySaveAndUpdateDto.builder().bookId(1L).build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update_shouldUpdateBookCopy() throws ServiceException {
        //given
        BookCopySaveAndUpdateDto expected = BookCopySaveAndUpdateDto.builder().id(4L).imagePath("image path").build();
        BookCopy bookCopy = BookCopy.builder().id(4L).imagePath("image path").build();
        //when
        when(bookCopyRepository.save(bookCopy))
                .thenReturn(bookCopy);
        boolean actual = bookCopyService.update(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteBookCopy() throws ServiceException {
        //given
        Long id = 3L;
        BookCopy bookCopy = BookCopy.builder().id(3L).status("EXISTS").book(Book.builder().authors(new HashSet<>()).genres(new HashSet<>()).build()).bookDamages(new HashSet<>()).build();

        //when
        when(bookCopyRepository.findById(id)).thenReturn(Optional.of(bookCopy));
        when(bookCopyRepository.save(bookCopy)).thenReturn(bookCopy);
        boolean actual = bookCopyService.delete(id);

        //then
        Assertions.assertTrue(actual);
    }
}