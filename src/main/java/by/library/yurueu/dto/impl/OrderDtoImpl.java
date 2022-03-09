package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.OrderDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
public class OrderDtoImpl implements OrderDto {
    private Long id;
    private String orderStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
    private Long userId;

    private List<BookDamageListDto> bookDamages;
    private List<BookCopyListDto> bookCopies;
}