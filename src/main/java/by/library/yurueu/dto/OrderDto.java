package by.library.yurueu.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class OrderDto {
    private Long id;
    private String orderStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
    private String deleteStatus;
    private Long userId;

    private List<BookDamageListDto> bookDamages;
    private List<BookCopyListDto> bookCopies;
}