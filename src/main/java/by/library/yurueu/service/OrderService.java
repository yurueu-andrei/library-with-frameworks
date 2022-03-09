package by.library.yurueu.service;

import by.library.yurueu.dto.OrderDto;
import by.library.yurueu.dto.OrderListDto;
import by.library.yurueu.dto.OrderSaveDto;
import by.library.yurueu.dto.OrderUpdateDto;
import by.library.yurueu.exception.ServiceException;

import java.util.Set;

public interface OrderService {
    OrderDto findById(Long id) throws ServiceException;
    Set<OrderListDto> findAll() throws ServiceException;
    OrderSaveDto add(OrderSaveDto orderSaveDto) throws ServiceException;
    boolean update(OrderUpdateDto orderUpdateDto) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
}