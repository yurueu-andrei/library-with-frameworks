package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.UserListDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserListDtoImpl implements UserListDto {
    private Long id;
    private String email;
    private String address;
}