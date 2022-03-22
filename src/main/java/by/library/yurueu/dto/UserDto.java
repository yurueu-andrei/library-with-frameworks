package by.library.yurueu.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private String email;
    private String address;
    private LocalDate birthDate;

    private List<Long> rolesId;
    private List<OrderListDto> orders;
}