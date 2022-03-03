package by.library.yurueu.dto;

import java.time.LocalDate;
import java.util.Set;

public interface AuthorDto {
    Long getId();
    String getFirstName();
    String getLastName();
    LocalDate getBirthDate();
    String getImagePath();
    Set<BookCopyListDto> getBooks();
}