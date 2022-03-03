package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.BookCopyListDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class BookCopyListDtoImpl implements BookCopyListDto {
    private Long id;
    private String title;
    private String imagePath;
    private int pricePerDay;
}