package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.UserUpdateDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserUpdateDtoImpl implements UserUpdateDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private String email;
    private String address;
    private LocalDate birthDate;
}
