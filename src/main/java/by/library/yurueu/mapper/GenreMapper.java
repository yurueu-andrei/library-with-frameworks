package by.library.yurueu.mapper;

import by.library.yurueu.dto.GenreDto;
import by.library.yurueu.entity.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDto toListDTO(Genre genre);
    List<GenreDto> toListDto(List<Genre> genres);
}