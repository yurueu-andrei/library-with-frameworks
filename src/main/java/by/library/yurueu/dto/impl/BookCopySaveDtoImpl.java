package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.BookCopySaveDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BookCopySaveDtoImpl implements BookCopySaveDto {
    private Long id;
    private String status;
    private LocalDate registrationDate;
    private int pricePerDay;
    private String imagePath;
    private Long bookId;
}