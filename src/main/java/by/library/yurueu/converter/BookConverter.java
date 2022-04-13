package by.library.yurueu.converter;

import by.library.yurueu.dto.BookSaveDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookConverter {
    public static BookSaveDto toSaveDTO(Book book) {
        return BookSaveDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .pagesNumber(book.getPagesNumber())
                .imagePath(book.getImagePath())
                .genresId(constructGenresId(book.getGenres()))
                .authorsId(constructAuthorsId(book.getAuthors()))
                .build();
    }

    private static List<Long> constructGenresId(Set<Genre> genres) {
        return genres.stream()
                .map(Genre::getId)
                .collect(Collectors.toList());
    }

    private static List<Long> constructAuthorsId(Set<Author> authors) {
        return authors.stream()
                .map(Author::getId)
                .collect(Collectors.toList());
    }

    public static Book fromSaveDTO(BookSaveDto bookSaveDto) {
        return Book.builder()
                .id(bookSaveDto.getId())
                .title(bookSaveDto.getTitle())
                .pagesNumber(bookSaveDto.getPagesNumber())
                .imagePath(bookSaveDto.getImagePath())
                .genres(constructGenres(bookSaveDto.getGenresId()))
                .authors(constructAuthors(bookSaveDto.getAuthorsId()))
                .build();
    }

    private static Set<Genre> constructGenres(List<Long> genresId) {
        return genresId.stream()
                .map(genreId -> Genre.builder().id(genreId).build())
                .collect(Collectors.toSet());
    }

    private static Set<Author> constructAuthors(List<Long> authorsId) {
        return authorsId.stream()
                .map(authorId -> Author.builder().id(authorId).build())
                .collect(Collectors.toSet());
    }
}