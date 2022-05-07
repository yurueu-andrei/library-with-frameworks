package by.library.yurueu.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Jacksonized
@Builder
@Value
public class OrderUpdateDto {
    Long id;
    String status;
    LocalDate startDate;
    LocalDate endDate;
    int price;
    Long userId;
}