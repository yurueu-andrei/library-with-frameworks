package by.library.yurueu.service.impl;

import by.library.yurueu.dto.OrderDto;
import by.library.yurueu.dto.OrderListDto;
import by.library.yurueu.dto.OrderSaveDto;
import by.library.yurueu.dto.OrderUpdateDto;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.mapper.OrderMapper;
import by.library.yurueu.repository.OrderRepository;
import by.library.yurueu.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto findById(Long id) throws ServiceException {
        return orderRepository.findById(id).map(orderMapper::toDTO)
                .orElseThrow(() -> new ServiceException(String.format("The order was not found. id = %d", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderListDto> findAll() throws ServiceException {
        try {
            return orderMapper.toListDto(orderRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException("The orders were not found", ex);
        }
    }

    @Transactional
    @Override
    public OrderSaveDto add(OrderSaveDto orderSaveDto) throws ServiceException {
        try {
            Order order = orderMapper.fromSaveDTO(orderSaveDto);
            order.setStatus("NEW");
            return orderMapper.toSaveDTO(orderRepository.save(order));
        } catch (Exception ex) {
            throw new ServiceException(String.format("The order was not saved. %s", orderSaveDto), ex);
        }
    }

    @Transactional
    @Override
    public boolean update(OrderUpdateDto orderUpdateDto) throws ServiceException {
        Order order = orderRepository.findById(orderUpdateDto.getId())
                .orElseThrow(()->
                        new ServiceException(
                                String.format(
                                        "The order was not updated. The order was not found. id = %d",
                                        orderUpdateDto.getId())));
        try {
            settingUpdatedFields(order, orderUpdateDto);
            orderRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The order was not updated. %s", orderUpdateDto), ex);
        }
    }

    private void settingUpdatedFields(Order order, OrderUpdateDto orderUpdateDto) {
        if (orderUpdateDto.getStatus() != null) {
            order.setStatus(orderUpdateDto.getStatus());
        }
        if (orderUpdateDto.getStartDate() != null) {
            order.setStartDate(orderUpdateDto.getStartDate());
        }
        if (orderUpdateDto.getEndDate() != null) {
            order.setEndDate(orderUpdateDto.getEndDate());
        }
        if (orderUpdateDto.getPrice() != 0) {
            order.setPrice(orderUpdateDto.getPrice());
        }
        if (orderUpdateDto.getUserId() != null) {
            order.setUser(User.builder().id(orderUpdateDto.getUserId()).build());
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The order was not deleted. The order was not found. id = %d", id)));
        try {
            order.setStatus("DELETED");
            orderRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The order was not deleted. id = %d", id), ex);
        }
    }
}