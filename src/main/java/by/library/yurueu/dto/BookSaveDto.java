package by.library.yurueu.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class BookSaveDto {
    private Long id;
    private String title;
    private int pagesNumber;
    private String imagePath;
    private List<Long> genresId;
    private List<Long> authorsId;
}