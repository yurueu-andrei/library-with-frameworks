package by.library.yurueu.dto;

import java.time.LocalDate;

public interface UserUpdateDto {
    Long getId();
    String getFirstName();
    String getLastName();
    String getPassportNumber();
    String getEmail();
    String getAddress();
    LocalDate getBirthDate();
}
