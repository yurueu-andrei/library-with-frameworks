package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;
import by.library.yurueu.repository.BaseRepositoryTest;
import by.library.yurueu.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstOrderInDB() {
        //given
        Order expected = findOrderForFindById();

        //when
        Optional<Order> order = orderRepository.findById(expected.getId());
        Order actual = order.orElse(Order.builder().build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllOrdersList() {
        //given
        List<Order> expected = findOrdersForFindAll();

        //when
        List<Order> actual = orderRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedOrder() {
        //given
        Order expected = Order.builder().id(6L).orderStatus("NEW").startDate(LocalDate.of(1999, 7, 6)).endDate(LocalDate.of(1988, 5, 6)).price(223).user(User.builder().id(1L).build()).build();
        Order actual = Order.builder().orderStatus("NEW").startDate(LocalDate.of(1999, 7, 6)).endDate(LocalDate.of(1988, 5, 6)).price(223).user(User.builder().id(1L).build()).build();

        //when
        actual = orderRepository.saveAndFlush(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, orderRepository.findById(expected.getId()).get());
    }

    @Test
    void updateTest_shouldUpdateOrder() {
        //given
        Order order = Order.builder().id(2L).orderStatus("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(243).user(User.builder().id(1L).build()).build();

        // when
        orderRepository.saveAndFlush(order);
        Optional<Order> foundOrder = orderRepository.findById(order.getId());

        //then
        Assertions.assertTrue(foundOrder.isPresent());
        Assertions.assertEquals(order, orderRepository.findById(order.getId()).get());
    }
}