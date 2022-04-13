package by.library.yurueu.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class BookCopySaveDto {
    private Long id;
    private String status;
    private LocalDate registrationDate;
    private int pricePerDay;
    private String imagePath;
    private String deleteStatus;
    private Long bookId;
}