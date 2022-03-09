package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.GenreUpdateDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class GenreUpdateDtoImpl implements GenreUpdateDto {
    private Long id;
    private String genreName;
}