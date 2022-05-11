package by.library.yurueu.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Jacksonized
@Builder
@Value
public class UserUpdateDto {
    Long id;
    String firstName;
    String lastName;
    String passportNumber;
    String email;
    String password;
    String address;
    LocalDate birthDate;
}
