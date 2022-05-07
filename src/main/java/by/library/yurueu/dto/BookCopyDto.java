package by.library.yurueu.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;

@Jacksonized
@Builder
@Value
public class BookCopyDto {
    Long id;
    String title;
    int pagesNumber;
    String status;
    LocalDate registrationDate;
    int pricePerDay;
    String imagePath;

    List<BookDamageListDto> bookDamages;
    List<GenreDto> genres;
    List<AuthorListDto> authors;
}