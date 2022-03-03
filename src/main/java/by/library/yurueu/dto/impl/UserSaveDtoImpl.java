package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.UserSaveDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Builder
public class UserSaveDtoImpl implements UserSaveDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private String email;
    private String address;
    private LocalDate birthDate;

    private Set<Long> rolesId;
}