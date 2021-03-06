package by.library.yurueu.controller;

import by.library.yurueu.dto.OrderDto;
import by.library.yurueu.dto.OrderListDto;
import by.library.yurueu.dto.OrderSaveDto;
import by.library.yurueu.dto.OrderUpdateDto;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasAuthority({'ORDER_READ'})")
    @GetMapping("/{id}")
    public OrderDto findById(@PathVariable Long id) throws ServiceException {
        return orderService.findById(id);
    }

    @PreAuthorize("hasAuthority({'ORDER_READ'})")
    @GetMapping
    public List<OrderListDto> findAll() throws ServiceException {
        return orderService.findAll();
    }

    @PreAuthorize("hasAuthority({'ORDER_WRITE'})")
    @PostMapping
    public OrderSaveDto add(
            @RequestBody OrderSaveDto orderSaveDto
    ) throws ServiceException {
        return orderService.add(orderSaveDto);
    }

    @PreAuthorize("hasAuthority({'ORDER_WRITE'})")
    @PutMapping
    public OrderUpdateDto update(
            @RequestBody OrderUpdateDto orderUpdateDto
    ) throws ServiceException {
        orderService.update(orderUpdateDto);
        return orderUpdateDto;
    }

    @PreAuthorize("hasAuthority({'ORDER_DELETE'})")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return orderService.delete(id);
    }
}