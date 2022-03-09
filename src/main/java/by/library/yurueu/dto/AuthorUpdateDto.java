package by.library.yurueu.dto;

import java.time.LocalDate;

public interface AuthorUpdateDto {
    Long getId();
    String getFirstName();
    String getLastName();
    LocalDate getBirthDate();
    String getImagePath();
}