package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.GenreListDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class GenreListDtoImpl implements GenreListDto {
    private Long id;
    private String genreName;
}