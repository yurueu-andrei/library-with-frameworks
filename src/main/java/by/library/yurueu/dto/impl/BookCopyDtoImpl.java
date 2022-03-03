package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
public class BookCopyDtoImpl implements BookCopyDto {
    private Long id;
    private String title;
    private int pagesNumber;
    private String status;
    private LocalDate registrationDate;
    private int pricePerDay;
    private String imagePath;

    private Set<BookDamageListDto> bookDamages;
    private Set<GenreListDto> genres;
    private Set<AuthorListDto> authors;
}