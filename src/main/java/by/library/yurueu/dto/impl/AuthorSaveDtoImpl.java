package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.AuthorSaveDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AuthorSaveDtoImpl implements AuthorSaveDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String imagePath;
}