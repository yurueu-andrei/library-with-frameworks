package by.library.yurueu.converter;

import by.library.yurueu.dto.GenreListDto;
import by.library.yurueu.dto.impl.GenreListDtoImpl;
import by.library.yurueu.entity.Genre;

import java.util.Set;
import java.util.stream.Collectors;

public class GenreConverter {
    public static GenreListDto toListDTO(Genre genre) {
        return GenreListDtoImpl.builder()
                .id(genre.getId())
                .genreName(genre.getGenreName())
                .build();
    }

    public static Set<GenreListDto> toListDTO(Set<Genre> genres) {
        return genres.stream()
                .map(GenreConverter::toListDTO)
                .collect(Collectors.toSet());
    }
}