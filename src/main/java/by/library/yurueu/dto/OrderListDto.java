package by.library.yurueu.dto;

import java.time.LocalDate;

public interface OrderListDto {
    Long getId();
    String getOrderStatus();
    LocalDate getStartDate();
    LocalDate getEndDate();
    int getPrice();
}