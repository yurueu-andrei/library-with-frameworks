package by.library.yurueu.dto;

import java.time.LocalDate;
import java.util.List;

public interface UserDto {
    Long getId();
    String getFirstName();
    String getLastName();
    String getPassportNumber();
    String getEmail();
    String getAddress();
    LocalDate getBirthDate();
    List<Long> getRolesId();
    List<OrderListDto> getOrders();
}