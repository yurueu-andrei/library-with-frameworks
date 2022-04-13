package by.library.yurueu.converter;

import by.library.yurueu.dto.OrderDto;
import by.library.yurueu.dto.OrderListDto;
import by.library.yurueu.dto.OrderSaveDto;
import by.library.yurueu.dto.OrderUpdateDto;
import by.library.yurueu.entity.BaseEntity;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderConverter {
    public static OrderDto toDTO(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .startDate(order.getStartDate())
                .endDate(order.getEndDate())
                .price(order.getPrice())
                .userId(order.getUser().getId())
                .bookCopies(BookCopyConverter.toListDTO(new ArrayList<>(order.getBookCopies())))
                .bookDamages(BookDamageConverter.toListDTO(new ArrayList<>(order.getBookDamages())))
                .deleteStatus(order.getDeleteStatus())
                .build();
    }

    public static OrderSaveDto toSaveDTO(Order order) {
        return OrderSaveDto.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .startDate(order.getStartDate())
                .endDate(order.getEndDate())
                .price(order.getPrice())
                .userId(order.getUser().getId())
                .bookCopiesId(constructBookCopiesId(order.getBookCopies()))
                .deleteStatus(order.getDeleteStatus())
                .build();
    }

    private static List<Long> constructBookCopiesId(Set<BookCopy> bookCopies) {
        return bookCopies.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    public static Order fromSaveDTO(OrderSaveDto orderSaveDto) {
        return Order.builder()
                .id(orderSaveDto.getId())
                .orderStatus(orderSaveDto.getOrderStatus())
                .startDate(orderSaveDto.getStartDate())
                .endDate(orderSaveDto.getEndDate())
                .price(orderSaveDto.getPrice())
                .user(User.builder().id(orderSaveDto.getUserId()).build())
                .bookCopies(constructBookCopies(orderSaveDto.getBookCopiesId()))
                .deleteStatus(orderSaveDto.getDeleteStatus())
                .build();
    }

    private static Set<BookCopy> constructBookCopies(List<Long> bookCopiesId) {
        return bookCopiesId.stream()
                .map(bookCopyId -> BookCopy.builder().id(bookCopyId).build())
                .collect(Collectors.toSet());
    }

    public static OrderListDto toListDTO(Order order) {
        return OrderListDto.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .startDate(order.getStartDate())
                .endDate(order.getEndDate())
                .price(order.getPrice())
                .build();
    }

    public static List<OrderListDto> toListDTO(List<Order> orders) {
        return orders.stream()
                .map(OrderConverter::toListDTO)
                .collect(Collectors.toList());
    }

    public static Order fromUpdateDTO(OrderUpdateDto orderUpdateDto) {
        return Order.builder()
                .id(orderUpdateDto.getId())
                .orderStatus(orderUpdateDto.getOrderStatus())
                .startDate(orderUpdateDto.getStartDate())
                .endDate(orderUpdateDto.getEndDate())
                .price(orderUpdateDto.getPrice())
                .deleteStatus(orderUpdateDto.getDeleteStatus())
                .build();
    }
}