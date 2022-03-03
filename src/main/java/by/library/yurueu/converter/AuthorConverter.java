package by.library.yurueu.converter;

import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.impl.AuthorDtoImpl;
import by.library.yurueu.dto.impl.AuthorListDtoImpl;
import by.library.yurueu.dto.impl.AuthorSaveDtoImpl;
import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorConverter {
    public static AuthorDto toDTO(Author author) {
        return AuthorDtoImpl.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .birthDate(author.getBirthDate())
                .imagePath(author.getImagePath())
                .books(constructBookCopiesListDto(author.getBooks()))
                .build();
    }

    private static Set<BookCopyListDto> constructBookCopiesListDto(Set<Book> books) {
        Set<BookCopyListDto> bookCopiesListDto = new HashSet<>();
        books.forEach(book -> {
            Set<BookCopy> bookCopies = book.getBookCopies();
            bookCopiesListDto.addAll(BookCopyConverter.toListDTO(bookCopies));

        });
        return bookCopiesListDto;
    }

    public static AuthorSaveDto toSaveDTO(Author author) {
        return AuthorSaveDtoImpl.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .birthDate(author.getBirthDate())
                .imagePath(author.getImagePath())
                .build();
    }

    public static Author fromSaveDTO(AuthorSaveDto authorSaveDto) {
        return Author.builder()
                .id(authorSaveDto.getId())
                .firstName(authorSaveDto.getFirstName())
                .lastName(authorSaveDto.getLastName())
                .birthDate(authorSaveDto.getBirthDate())
                .imagePath(authorSaveDto.getImagePath())
                .build();
    }

    public static AuthorListDto toListDTO(Author author) {
        return AuthorListDtoImpl.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public static Set<AuthorListDto> toListDTO(Set<Author> authors) {
        return authors.stream()
                .map(AuthorConverter::toListDTO)
                .collect(Collectors.toSet());
    }
}