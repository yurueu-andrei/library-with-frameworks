package by.library.yurueu.dto;

import java.time.LocalDate;

public interface OrderUpdateDto {
    Long getId();
    String getOrderStatus();
    LocalDate getStartDate();
    LocalDate getEndDate();
    int getPrice();
}