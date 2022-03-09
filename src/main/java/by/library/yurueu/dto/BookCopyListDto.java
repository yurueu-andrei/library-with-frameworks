package by.library.yurueu.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class BookCopyListDto {
    private Long id;
    private String title;
    private String imagePath;
    private int pricePerDay;
}