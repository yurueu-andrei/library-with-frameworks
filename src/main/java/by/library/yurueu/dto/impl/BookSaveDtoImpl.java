package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.BookSaveDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class BookSaveDtoImpl implements BookSaveDto {
    private Long id;
    private String title;
    private int pagesNumber;
    private String imagePath;
    private Set<Long> genresId;
    private Set<Long> authorsId;
}