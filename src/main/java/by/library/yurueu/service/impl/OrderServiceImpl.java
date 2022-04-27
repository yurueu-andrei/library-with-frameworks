package by.library.yurueu.service.impl;

import by.library.yurueu.dto.OrderDto;
import by.library.yurueu.dto.OrderListDto;
import by.library.yurueu.dto.OrderSaveDto;
import by.library.yurueu.dto.OrderUpdateDto;
import by.library.yurueu.entity.Order;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.mapper.OrderMapper;
import by.library.yurueu.repository.OrderRepository;
import by.library.yurueu.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto findById(Long id) throws ServiceException {
        return orderRepository.findById(id).map(orderMapper::toDTO)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderListDto> findAll() throws ServiceException {
        try {
            return orderMapper.toListDto(orderRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName() + "s", "were not found"));
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
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not added"));
        }
    }

    @Transactional
    @Override
    public boolean update(OrderUpdateDto orderUpdateDto) throws ServiceException {
        try {
            Order order = orderMapper.fromUpdateDTO(orderUpdateDto);
            orderRepository.save(order);
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not updated"));
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        Optional<Order> orderToDelete = orderRepository.findById(id);
        if (orderToDelete.isPresent()) {
            Order order = orderToDelete.get();
            order.setStatus("DELETED");
            orderRepository.save(order);
            return true;
        }
        throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not deleted"));
    }
}