package by.library.yurueu.dto;

import java.time.LocalDate;
import java.util.Set;

public interface OrderDto {
    Long getId();
    String getOrderStatus();
    LocalDate getStartDate();
    LocalDate getEndDate();
    int getPrice();
    Long getUserId();

    Set<BookDamageListDto> getBookDamages();
    Set<BookCopyListDto> getBookCopies();
}