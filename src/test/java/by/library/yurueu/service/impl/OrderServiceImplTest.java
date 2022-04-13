package by.library.yurueu.service.impl;

import by.library.yurueu.dto.OrderDto;
import by.library.yurueu.dto.OrderListDto;
import by.library.yurueu.dto.OrderSaveDto;
import by.library.yurueu.dto.OrderUpdateDto;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;
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
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void findById_shouldReturnOrderDto() throws ServiceException {
        Long id = 1L;
        BookCopy bookCopy = BookCopy.builder().id(2L).book(Book.builder().id(id).build())
                .imagePath("imagePath").pricePerDay(13).build();
        OrderDto expected = OrderDto.builder().id(id).userId(2L)
                .bookCopies(new ArrayList<>())
                .bookDamages(new ArrayList<>())
                .build();

        //when
        when(orderRepository.findById(id))
                .thenReturn(Optional.of(Order.builder().id(id).user(User.builder().id(2L).build())
                        .bookCopies(new HashSet<>()).bookDamages(new HashSet<>()).build()));
        OrderDto actual = orderService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldReturnListOfOrderListDto() throws ServiceException {
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
    void add_shouldAddOrder() throws ServiceException {
        //given
        OrderSaveDto expected = OrderSaveDto.builder()
                .id(3L)
                .price(13)
                .userId(1L)
                .bookCopiesId(new ArrayList<>(){{add(2L);}}).build();
        Order orderWithoutId = Order.builder()
                .user(User.builder().id(1L).build())
                .bookCopies(new HashSet<>(){{add(BookCopy.builder().id(2L).build());}})
                .price(13).build();
        Order orderWithId = Order.builder()
                .id(3L)
                .user(User.builder().id(1L).build())
                .bookCopies(new HashSet<>(){{add(BookCopy.builder().id(2L).build());}})
                .price(13).build();

        //when
        when(orderRepository.save(orderWithoutId)).thenReturn(orderWithId);
        OrderSaveDto actual = orderService.add(OrderSaveDto.builder()
                .price(13)
                .userId(1L)
                .bookCopiesId(new ArrayList<>(){{add(2L);}}).build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update_shouldUpdateOrder() throws ServiceException {
        //given
        OrderUpdateDto expected = OrderUpdateDto.builder().id(4L).status("ACCEPTED").build();
        Order order = Order.builder().id(4L).status("ACCEPTED").build();

        //when
        when(orderRepository.save(order)).thenReturn(order);
        boolean actual = orderService.update(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteOrder() throws ServiceException {
        //given
        Long id = 3L;
        Order order = Order.builder().id(3L).status("DELETED").build();

        //when
        when(orderRepository.findById(id)).thenReturn(Optional.of(Order.builder().id(3L).status("COMPLETED").build()));
        when(orderRepository.save(order)).thenReturn(order);
        boolean actual = orderService.delete(id);

        //then
        Assertions.assertTrue(actual);
    }
}