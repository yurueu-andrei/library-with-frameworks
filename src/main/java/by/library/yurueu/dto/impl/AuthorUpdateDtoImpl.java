package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.AuthorUpdateDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@EqualsAndHashCode
public class AuthorUpdateDtoImpl implements AuthorUpdateDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String imagePath;
}