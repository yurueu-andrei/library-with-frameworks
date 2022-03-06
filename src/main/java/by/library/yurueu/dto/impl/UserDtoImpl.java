package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.OrderListDto;
import by.library.yurueu.dto.UserDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder
@EqualsAndHashCode
public class UserDtoImpl implements UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private String email;
    private String address;
    private LocalDate birthDate;

    private Set<Long> rolesId;
    private Set<OrderListDto> orders;
}