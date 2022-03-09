package by.library.yurueu.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String imagePath;
    private List<BookCopyListDto> books;
}