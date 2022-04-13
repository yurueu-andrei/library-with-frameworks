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
public class OrderUpdateDto {
    private Long id;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
}