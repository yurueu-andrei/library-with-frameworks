package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.AuthorListDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthorListDtoImpl implements AuthorListDto {
    private Long id;
    private String firstName;
    private String lastName;
}