package by.library.yurueu.converter;

import by.library.yurueu.dto.OrderDto;
import by.library.yurueu.dto.OrderListDto;
import by.library.yurueu.dto.OrderSaveDto;
import by.library.yurueu.dto.impl.OrderDtoImpl;
import by.library.yurueu.dto.impl.OrderListDtoImpl;
import by.library.yurueu.dto.impl.OrderSaveDtoImpl;
import by.library.yurueu.entity.BaseEntity;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public class OrderConverter {
    public static OrderDto toDTO(Order order) {
        return OrderDtoImpl.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .startDate(order.getStartDate())
                .endDate(order.getEndDate())
                .price(order.getPrice())
                .userId(order.getUser().getId())
                .bookCopies(BookCopyConverter.toListDTO(order.getBookCopies()))
                .bookDamages(BookDamageConverter.toListDTO(order.getBookDamages()))
                .build();
    }

    public static OrderSaveDto toSaveDTO(Order order) {
        return OrderSaveDtoImpl.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .startDate(order.getStartDate())
                .endDate(order.getEndDate())
                .price(order.getPrice())
                .userId(order.getUser().getId())
                .bookCopiesId(constructBookCopiesId(order.getBookCopies()))
                .build();
    }

    private static Set<Long> constructBookCopiesId(Set<BookCopy> bookCopies) {
        return bookCopies.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
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
                .build();
    }

    private static Set<BookCopy> constructBookCopies(Set<Long> bookCopiesId) {
        return bookCopiesId.stream()
                .map(bookCopyId -> BookCopy.builder().id(bookCopyId).build())
                .collect(Collectors.toSet());
    }

    public static OrderListDto toListDTO(Order order) {
        return OrderListDtoImpl.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .startDate(order.getStartDate())
                .endDate(order.getEndDate())
                .price(order.getPrice())
                .build();
    }

    public static Set<OrderListDto> toListDTO(Set<Order> orders) {
        return orders.stream()
                .map(OrderConverter::toListDTO)
                .collect(Collectors.toSet());
    }
}