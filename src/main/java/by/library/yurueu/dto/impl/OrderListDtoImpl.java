package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.OrderListDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class OrderListDtoImpl implements OrderListDto {
    private Long id;
    private String orderStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
}