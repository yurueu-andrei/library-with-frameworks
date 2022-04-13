package by.library.yurueu.converter;

import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveDto;
import by.library.yurueu.dto.AuthorUpdateDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorConverter {
    public static AuthorDto toDTO(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .birthDate(author.getBirthDate())
                .imagePath(author.getImagePath())
                .books(constructBookCopiesListDTO(author.getBooks()))
                .deleteStatus(author.getDeleteStatus())
                .build();
    }

    private static List<BookCopyListDto> constructBookCopiesListDTO(Set<Book> books) {
        List<BookCopyListDto> bookCopiesListDto = new ArrayList<>();
        books.forEach(book -> {
            List<BookCopy> bookCopies = new ArrayList<>(book.getBookCopies());
            bookCopiesListDto.addAll(BookCopyConverter.toListDTO(bookCopies));
        });
        return bookCopiesListDto;
    }

    public static AuthorSaveDto toSaveDTO(Author author) {
        return AuthorSaveDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .birthDate(author.getBirthDate())
                .imagePath(author.getImagePath())
                .deleteStatus(author.getDeleteStatus())
                .build();
    }

    public static Author fromSaveDTO(AuthorSaveDto authorSaveDto) {
        return Author.builder()
                .id(authorSaveDto.getId())
                .firstName(authorSaveDto.getFirstName())
                .lastName(authorSaveDto.getLastName())
                .birthDate(authorSaveDto.getBirthDate())
                .imagePath(authorSaveDto.getImagePath())
                .deleteStatus(authorSaveDto.getDeleteStatus())
                .build();
    }

    public static AuthorListDto toListDTO(Author author) {
        return AuthorListDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public static List<AuthorListDto> toListDTO(List<Author> authors) {
        return authors.stream()
                .map(AuthorConverter::toListDTO)
                .collect(Collectors.toList());
    }

    public static Author fromUpdateDTO(AuthorUpdateDto authorUpdateDto) {
        return Author.builder()
                .id(authorUpdateDto.getId())
                .firstName(authorUpdateDto.getFirstName())
                .lastName(authorUpdateDto.getLastName())
                .birthDate(authorUpdateDto.getBirthDate())
                .imagePath(authorUpdateDto.getImagePath())
                .deleteStatus(authorUpdateDto.getDeleteStatus())
                .build();
    }
}