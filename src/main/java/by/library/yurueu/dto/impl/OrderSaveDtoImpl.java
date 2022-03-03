package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.OrderSaveDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
public class OrderSaveDtoImpl implements OrderSaveDto {
    private Long id;
    private String orderStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
    private Long userId;
    private Set<Long> bookCopiesId;
}