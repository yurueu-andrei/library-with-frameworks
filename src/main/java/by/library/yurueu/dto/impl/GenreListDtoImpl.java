package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.GenreListDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenreListDtoImpl implements GenreListDto {
    private Long id;
    private String genreName;
}