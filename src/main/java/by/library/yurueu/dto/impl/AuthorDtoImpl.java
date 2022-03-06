package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.BookCopyListDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@EqualsAndHashCode
public class AuthorDtoImpl implements AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String imagePath;
    private Set<BookCopyListDto> books;
}