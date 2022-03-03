package by.library.yurueu.dto;

import java.time.LocalDate;

public interface AuthorSaveDto {
    Long getId();
    String getFirstName();
    String getLastName();
    LocalDate getBirthDate();
    String getImagePath();

    void setId(Long id);
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setBirthDate(LocalDate birthDate);
    void setImagePath(String imagePath);
}