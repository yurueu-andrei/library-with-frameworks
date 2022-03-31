package by.library.yurueu.service.impl;

import by.library.yurueu.dto.BookDamageDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.BookDamageSaveDto;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.BookDamageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class BookDamageServiceImplTest {
    @Mock
    private BookDamageRepository bookDamageRepository;
    @InjectMocks
    private BookDamageServiceImpl bookDamageService;

    @Test
    void findById_shouldReturnBookDamageDto() throws ServiceException, RepositoryException {
        //given
        Long id = 1L;
        BookDamageDto expected = BookDamageDto.builder().id(id).userId(1L).bookCopyId(1L).orderId(1L).build();

        //when
        when(bookDamageRepository.findById(id)).thenReturn(BookDamage.builder().id(id)
                .bookCopy(BookCopy.builder().id(1L).build())
                .user(User.builder().id(1L).build())
                .order(Order.builder().id(1L).build())
                .build());
        BookDamageDto actual = bookDamageService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldReturnListOfBookDamageListDto() throws RepositoryException, ServiceException {
        //given
        List<BookDamageListDto> expected = new ArrayList<>() {{
            add(BookDamageListDto.builder().id(1L).build());
            add(BookDamageListDto.builder().id(2L).build());
        }};

        //when
        when(bookDamageRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(BookDamage.builder().id(1L).build());
            add(BookDamage.builder().id(2L).build());
        }});
        List<BookDamageListDto> actual = bookDamageService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void add_shouldAddBookDamage() throws RepositoryException, ServiceException {
        //given
        BookDamageSaveDto expected = BookDamageSaveDto.builder().id(3L).bookCopyId(1L).orderId(1L).userId(1L).build();

        //when
        when(bookDamageRepository.add(BookDamage.builder()
                .bookCopy(BookCopy.builder().id(1L).build())
                .order(Order.builder().id(1L).build())
                .user(User.builder().id(1L).build()).build()))
                .thenReturn(BookDamage.builder()
                        .id(3L)
                        .bookCopy(BookCopy.builder().id(1L).build())
                        .order(Order.builder().id(1L).build())
                        .user(User.builder().id(1L).build()).build());
        BookDamageSaveDto actual = bookDamageService.add(BookDamageSaveDto.builder()
                .bookCopyId(1L).orderId(1L).userId(1L).build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete_shouldDeleteBookDamage() throws RepositoryException, ServiceException {
        //given
        Long id = 3L;

        //when
        when(bookDamageRepository.delete(id)).thenReturn(true);
        boolean actual = bookDamageService.delete(id);

        //then
        Assertions.assertTrue(actual);
        Assertions.assertThrows(ServiceException.class, () -> bookDamageService.findById(id));
    }
}