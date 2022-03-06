package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.BookCopyListDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class BookCopyListDtoImpl implements BookCopyListDto {
    private Long id;
    private String title;
    private String imagePath;
    private int pricePerDay;
}