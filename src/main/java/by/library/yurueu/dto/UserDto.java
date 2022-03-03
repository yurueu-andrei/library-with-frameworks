package by.library.yurueu.dto;

import java.time.LocalDate;
import java.util.Set;

public interface UserDto {
    Long getId();
    String getFirstName();
    String getLastName();
    String getPassportNumber();
    String getEmail();
    String getAddress();
    LocalDate getBirthDate();
    Set<Long> getRolesId();
    Set<OrderListDto> getOrders();
}