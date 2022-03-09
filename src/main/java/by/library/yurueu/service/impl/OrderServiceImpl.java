package by.library.yurueu.service.impl;

import by.library.yurueu.converter.OrderConverter;
import by.library.yurueu.dto.OrderDto;
import by.library.yurueu.dto.OrderListDto;
import by.library.yurueu.dto.OrderSaveDto;
import by.library.yurueu.dto.OrderUpdateDto;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.OrderRepository;
import by.library.yurueu.repository.UserRepository;
import by.library.yurueu.repository.impl.OrderRepositoryImpl;
import by.library.yurueu.repository.impl.UserRepositoryImpl;
import by.library.yurueu.service.OrderService;

import java.util.HashSet;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl() {
        orderRepository = new OrderRepositoryImpl();
        userRepository = new UserRepositoryImpl();
    }

    @Override
    public OrderDto findById(Long id) throws ServiceException {
        try {
            Order order = orderRepository.findById(id);
            order.setUser(userRepository.findById(order.getUser().getId()));
            order.setBookCopies(orderRepository.findBookCopiesByOrderId(id));
            order.setBookDamages(orderRepository.findBookDamagesByOrderId(id));
            return OrderConverter.toDTO(order);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public List<OrderListDto> findAll() throws ServiceException {
        try {
            List<Order> orders = orderRepository.findAll();
            return OrderConverter.toListDTO(new HashSet<>(orders));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public OrderSaveDto add(OrderSaveDto orderSaveDto) throws ServiceException {
        try {
            Order order = OrderConverter.fromSaveDTO(orderSaveDto);
            order.setUser(User.builder().id(orderSaveDto.getUserId()).build());
            order.setBookCopies(orderRepository.findBookCopiesByBookCopiesId(new HashSet<>(orderSaveDto.getBookCopiesId())));
            return OrderConverter.toSaveDTO(orderRepository.add(order));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean update(OrderUpdateDto orderUpdateDto) throws ServiceException {
        try {
            Order order = OrderConverter.fromUpdateDto(orderUpdateDto);
            return orderRepository.update(order);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return orderRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }
}