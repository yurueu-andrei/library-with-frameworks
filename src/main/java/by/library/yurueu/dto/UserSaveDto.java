package by.library.yurueu.dto;

import java.time.LocalDate;
import java.util.Set;

public interface UserSaveDto {
    Long getId();
    String getFirstName();
    String getLastName();
    String getPassportNumber();
    String getEmail();
    String getAddress();
    LocalDate getBirthDate();
    Set<Long> getRolesId();

    void setId(Long id);
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setPassportNumber(String passportNumber);
    void setEmail(String email);
    void setAddress(String address);
    void setBirthDate(LocalDate birthDate);
    void setRolesId(Set<Long> rolesId);
}