package by.library.yurueu.dto;

import java.time.LocalDate;

public interface BookCopySaveDto {
    Long getId();
    String getStatus();
    LocalDate getRegistrationDate();
    int getPricePerDay();
    String getImagePath();
    Long getBookId();

    void setId(Long id);
    void setStatus(String status);
    void setRegistrationDate(LocalDate registrationDate);
    void setPricePerDay(int pricePerDay);
    void setImagePath(String imagePath);
    void setBookId(Long bookId);
}