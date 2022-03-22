package by.library.yurueu.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class OrderListDto {
    private Long id;
    private String orderStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
}