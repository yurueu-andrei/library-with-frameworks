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
public class BookCopyDto {
    private Long id;
    private String title;
    private int pagesNumber;
    private String status;
    private LocalDate registrationDate;
    private int pricePerDay;
    private String imagePath;

    private List<BookDamageListDto> bookDamages;
    private List<GenreListDto> genres;
    private List<AuthorListDto> authors;
}