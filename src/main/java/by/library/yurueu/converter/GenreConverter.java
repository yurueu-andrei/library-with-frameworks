package by.library.yurueu.converter;

import by.library.yurueu.dto.GenreDto;
import by.library.yurueu.entity.Genre;

import java.util.List;
import java.util.stream.Collectors;

public class GenreConverter {
    public static GenreDto toListDTO(Genre genre) {
        return GenreDto.builder()
                .id(genre.getId())
                .genreName(genre.getGenreName())
                .build();
    }

    public static List<GenreDto> toListDTO(List<Genre> genres) {
        return genres.stream()
                .map(GenreConverter::toListDTO)
                .collect(Collectors.toList());
    }
}