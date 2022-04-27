package by.library.yurueu.mapper;

import by.library.yurueu.dto.BookSaveDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "genresId", source = "book.genres")
    @Mapping(target = "authorsId", source = "book.authors")
    BookSaveDto toSaveDTO(Book book);

    @Mapping(target = "genres", source = "bookSaveDto.genresId")
    @Mapping(target = "authors", source = "bookSaveDto.authorsId")
    Book fromSaveDTO(BookSaveDto bookSaveDto);

    default Set<Author> authorsIdToAuthors(List<Long> authorsId) {
        return authorsId.stream().map(id -> Author.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    default Set<Genre> genresIdToGenres(List<Long> genresId) {
        return genresId.stream().map(id -> Genre.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    default List<Long> genresIdToGenres(Set<Genre> genres) {
        return genres.stream().map(Genre::getId)
                .collect(Collectors.toList());
    }

    default List<Long> authorsIdToAuthors(Set<Author> authors) {
        return authors.stream().map(Author::getId)
                .collect(Collectors.toList());
    }
}