package by.library.yurueu.dto;

import java.time.LocalDate;
import java.util.List;

public interface OrderDto {
    Long getId();
    String getOrderStatus();
    LocalDate getStartDate();
    LocalDate getEndDate();
    int getPrice();
    Long getUserId();

    List<BookDamageListDto> getBookDamages();
    List<BookCopyListDto> getBookCopies();
}