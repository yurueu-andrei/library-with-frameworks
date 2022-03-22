package by.library.yurueu.converter;

import by.library.yurueu.dto.GenreListDto;
import by.library.yurueu.dto.GenreUpdateDto;
import by.library.yurueu.entity.Genre;

import java.util.List;
import java.util.stream.Collectors;

public class GenreConverter {
    public static GenreListDto toListDTO(Genre genre) {
        return GenreListDto.builder()
                .id(genre.getId())
                .genreName(genre.getGenreName())
                .build();
    }

    public static List<GenreListDto> toListDTO(List<Genre> genres) {
        return genres.stream()
                .map(GenreConverter::toListDTO)
                .collect(Collectors.toList());
    }

    public static Genre fromUpdateDTO(GenreUpdateDto genreUpdateDto) {
        return Genre.builder()
                .id(genreUpdateDto.getId())
                .genreName(genreUpdateDto.getGenreName())
                .build();
    }

    public static Genre fromListDTO(GenreListDto genreListDto) {
        return Genre.builder()
                .id(genreListDto.getId())
                .genreName(genreListDto.getGenreName())
                .build();
    }
}