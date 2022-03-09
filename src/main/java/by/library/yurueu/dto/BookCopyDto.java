package by.library.yurueu.dto;

import java.time.LocalDate;
import java.util.List;

public interface BookCopyDto {
    Long getId();
    String getTitle();
    int getPagesNumber();
    int getPricePerDay();
    LocalDate getRegistrationDate();
    String getImagePath();
    String getStatus();
    List<BookDamageListDto> getBookDamages();
    List<GenreListDto> getGenres() ;
    List<AuthorListDto> getAuthors();
}