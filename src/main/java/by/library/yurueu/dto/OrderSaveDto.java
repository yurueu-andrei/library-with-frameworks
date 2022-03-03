package by.library.yurueu.dto;

import java.time.LocalDate;
import java.util.Set;

public interface OrderSaveDto {
    Long getId();
    String getOrderStatus();
    LocalDate getStartDate();
    LocalDate getEndDate();
    int getPrice();
    Long getUserId();
    Set<Long> getBookCopiesId();

    void setId(Long id);
    void setOrderStatus(String orderStatus);
    void setStartDate(LocalDate startDate);
    void setEndDate(LocalDate endDate);
    void setPrice(int price);
    void setUserId(Long userId);
    void setBookCopiesId(Set<Long> bookCopiesId);
}