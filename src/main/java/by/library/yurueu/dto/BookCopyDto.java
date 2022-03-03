package by.library.yurueu.dto;

import java.time.LocalDate;
import java.util.Set;

public interface BookCopyDto {
    Long getId();
    String getTitle();
    int getPagesNumber();
    int getPricePerDay();
    LocalDate getRegistrationDate();
    String getImagePath();
    String getStatus();
    Set<BookDamageListDto> getBookDamages();
    Set<GenreListDto> getGenres() ;
    Set<AuthorListDto> getAuthors();
}