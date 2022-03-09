package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.BookCopyDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.GenreListDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class BookCopyDtoImpl implements BookCopyDto {
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