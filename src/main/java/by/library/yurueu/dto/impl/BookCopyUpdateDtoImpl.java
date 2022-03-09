package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.BookCopyUpdateDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode
public class BookCopyUpdateDtoImpl implements BookCopyUpdateDto {
    private Long id;
    private String status;
    private LocalDate registrationDate;
    private int pricePerDay;
    private String imagePath;
}