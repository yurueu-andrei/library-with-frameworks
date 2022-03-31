package by.library.yurueu.service.impl;

import by.library.yurueu.converter.BookCopyConverter;
import by.library.yurueu.dto.OrderDto;
import by.library.yurueu.dto.OrderListDto;
import by.library.yurueu.dto.OrderSaveDto;
import by.library.yurueu.dto.OrderUpdateDto;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.OrderRepository;
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
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void findById_shouldReturnOrderDto() throws RepositoryException, ServiceException {
        Long id = 1L;
        BookCopy bookCopy = BookCopy.builder().id(2L).book(Book.builder().id(id).build())
                .imagePath("imagePath").pricePerDay(13).build();
        OrderDto expected = OrderDto.builder().id(id).userId(2L)
                .bookCopies(new ArrayList<>(){{add(BookCopyConverter.toListDTO(bookCopy));}})
                .bookDamages(new ArrayList<>()).build();

        //when
        when(orderRepository.findById(id)).thenReturn(Order.builder().id(id).user(User.builder().id(2L).build()).build());
        when(orderRepository.findBookCopiesByOrderId(id)).thenReturn(new HashSet<>(){{add(bookCopy);}});
        when(orderRepository.findBookDamagesByOrderId(id)).thenReturn(new HashSet<>());
        OrderDto actual = orderService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldReturnListOfOrderListDto() throws RepositoryException, ServiceException {
        //given
        List<OrderListDto> expected = new ArrayList<>() {{
            add(OrderListDto.builder().id(1L).build());
            add(OrderListDto.builder().id(2L).build());
        }};

        //when
        when(orderRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(Order.builder().id(1L).build());
            add(Order.builder().id(2L).build());
        }});
        List<OrderListDto> actual = orderService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void add_shouldAddOrder() throws RepositoryException, ServiceException {
        //given
        OrderSaveDto expected = OrderSaveDto.builder()
                .id(3L)
                .price(13)
                .userId(1L)
                .bookCopiesId(new ArrayList<>(){{add(2L);}}).build();

        //when
        when(orderRepository.findBookCopiesByBookCopiesId(new HashSet<>(){{add(2L);}}))
                .thenReturn(new HashSet<>(){{add(BookCopy.builder().id(2L).build());}});

        when(orderRepository.add(Order.builder()
                .user(User.builder().id(1L).build())
                .bookCopies(new HashSet<>(){{add(BookCopy.builder().id(2L).build());}})
                .price(13).build()))
                .thenReturn(Order.builder()
                        .id(3L)
                        .user(User.builder().id(1L).build())
                        .bookCopies(new HashSet<>(){{add(BookCopy.builder().id(2L).build());}})
                        .price(13).build());
        OrderSaveDto actual = orderService.add(OrderSaveDto.builder()
                .price(13)
                .userId(1L)
                .bookCopiesId(new ArrayList<>(){{add(2L);}}).build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update_shouldUpdateOrder() throws RepositoryException, ServiceException {
        //given
        OrderUpdateDto expected = OrderUpdateDto.builder().id(4L).orderStatus("ACCEPTED").build();

        //when
        when(orderRepository.update(Order.builder().id(4L).orderStatus("ACCEPTED").build())).thenReturn(true);
        boolean actual = orderService.update(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteOrder() throws RepositoryException, ServiceException {
        //given
        Long id = 3L;

        //when
        when(orderRepository.delete(id)).thenReturn(true);
        boolean actual = orderService.delete(id);

        //then
        Assertions.assertTrue(actual);
        Assertions.assertThrows(ServiceException.class, () -> orderService.findById(id));
    }
}