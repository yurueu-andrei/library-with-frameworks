package by.library.yurueu.dto;

import java.time.LocalDate;
import java.util.List;

public interface AuthorDto {
    Long getId();
    String getFirstName();
    String getLastName();
    LocalDate getBirthDate();
    String getImagePath();
    List<BookCopyListDto> getBooks();
}