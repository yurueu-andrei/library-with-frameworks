package by.library.yurueu.dto;

import java.time.LocalDate;

public interface BookCopyUpdateDto {
    Long getId();
    String getStatus();
    LocalDate getRegistrationDate();
    int getPricePerDay();
    String getImagePath();
}