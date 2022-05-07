package by.library.yurueu.dto;

import lombok.Value;

@Value
public class AuthenticationRequestDto {
    String username;
    String password;
}