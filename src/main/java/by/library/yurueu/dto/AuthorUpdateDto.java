package by.library.yurueu.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class AuthorUpdateDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String imagePath;
}