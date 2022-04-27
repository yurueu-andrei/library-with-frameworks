package by.library.yurueu.mapper;

import by.library.yurueu.dto.OrderDto;
import by.library.yurueu.dto.OrderListDto;
import by.library.yurueu.dto.OrderSaveDto;
import by.library.yurueu.dto.OrderUpdateDto;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "userId", source = "order.user.id")
    OrderDto toDTO(Order order);

    @Mapping(target = "bookCopiesId", source = "order.bookCopies")
    @Mapping(target = "userId", source = "order.user.id")
    OrderSaveDto toSaveDTO(Order order);

    @Mapping(target = "user.id", source = "orderSaveDto.userId")
    @Mapping(target = "bookCopies", source = "orderSaveDto.bookCopiesId")
    Order fromSaveDTO(OrderSaveDto orderSaveDto);

    @Mapping(target = "user.id", source = "orderUpdateDto.userId")
    Order fromUpdateDTO(OrderUpdateDto orderUpdateDto);

    OrderListDto toListDTO(Order order);

    List<OrderListDto> toListDto(List<Order> orders);

    default Set<BookCopy> bookCopiesIdToBookCopies(List<Long> ids) {
        return ids.stream().map(id -> BookCopy.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    default List<Long> bookCopiesToBookCopiesId(Set<BookCopy> bookCopies) {
        return bookCopies.stream().map(BookCopy::getId)
                .collect(Collectors.toList());
    }
}