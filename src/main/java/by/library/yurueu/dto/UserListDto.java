package by.library.yurueu.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class UserListDto {
    Long id;
    String email;
    String address;
}