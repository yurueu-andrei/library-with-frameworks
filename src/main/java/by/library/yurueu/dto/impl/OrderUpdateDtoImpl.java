package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.OrderUpdateDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode
public class OrderUpdateDtoImpl implements OrderUpdateDto {
    private Long id;
    private String orderStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
}