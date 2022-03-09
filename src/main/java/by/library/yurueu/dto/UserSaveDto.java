package by.library.yurueu.dto;

import java.time.LocalDate;
import java.util.List;

public interface UserSaveDto {
    Long getId();
    String getFirstName();
    String getLastName();
    String getPassportNumber();
    String getEmail();
    String getAddress();
    LocalDate getBirthDate();
    List<Long> getRolesId();

    void setId(Long id);
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setPassportNumber(String passportNumber);
    void setEmail(String email);
    void setAddress(String address);
    void setBirthDate(LocalDate birthDate);
    void setRolesId(List<Long> rolesId);
}