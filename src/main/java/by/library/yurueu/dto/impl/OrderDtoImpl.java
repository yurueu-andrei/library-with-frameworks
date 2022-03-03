package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.OrderDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder
public class OrderDtoImpl implements OrderDto {
    private Long id;
    private String orderStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
    private Long userId;

    private Set<BookDamageListDto> bookDamages;
    private Set<BookCopyListDto> bookCopies;
}
