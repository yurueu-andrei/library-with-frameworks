package by.library.yurueu.converter;

import by.library.yurueu.dto.BookCopyDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookCopySaveDto;
import by.library.yurueu.dto.BookCopyUpdateDto;
import by.library.yurueu.dto.impl.BookCopyDtoImpl;
import by.library.yurueu.dto.impl.BookCopyListDtoImpl;
import by.library.yurueu.dto.impl.BookCopySaveDtoImpl;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookCopyConverter {
    public static BookCopyDto toDTO(BookCopy bookCopy) {
        return BookCopyDtoImpl.builder()
                .id(bookCopy.getId())
                .title(bookCopy.getBook().getTitle())
                .pagesNumber(bookCopy.getBook().getPagesNumber())
                .status(bookCopy.getStatus())
                .registrationDate(bookCopy.getRegistrationDate())
                .pricePerDay(bookCopy.getPricePerDay())
                .imagePath(bookCopy.getImagePath())
                .bookDamages(BookDamageConverter.toListDTO(bookCopy.getBookDamages()))
                .authors(AuthorConverter.toListDTO(bookCopy.getBook().getAuthors()))
                .genres(GenreConverter.toListDTO(bookCopy.getBook().getGenres()))
                .build();
    }

    public static BookCopySaveDto toSaveDTO(BookCopy bookCopy) {
        return BookCopySaveDtoImpl.builder()
                .id(bookCopy.getId())
                .status(bookCopy.getStatus())
                .registrationDate(bookCopy.getRegistrationDate())
                .imagePath(bookCopy.getImagePath())
                .pricePerDay(bookCopy.getPricePerDay())
                .bookId(bookCopy.getBook().getId())
                .build();
    }

    public static BookCopy fromSaveDTO(BookCopySaveDto bookCopySaveDto) {
        return BookCopy.builder()
                .id(bookCopySaveDto.getId())
                .status(bookCopySaveDto.getStatus())
                .registrationDate(bookCopySaveDto.getRegistrationDate())
                .imagePath(bookCopySaveDto.getImagePath())
                .pricePerDay(bookCopySaveDto.getPricePerDay())
                .book(Book.builder().id(bookCopySaveDto.getBookId()).build())
                .build();
    }

    public static BookCopyListDto toListDTO(BookCopy bookCopy) {
        return BookCopyListDtoImpl.builder()
                .id(bookCopy.getId())
                .title(bookCopy.getBook().getTitle())
                .imagePath(bookCopy.getImagePath())
                .pricePerDay(bookCopy.getPricePerDay())
                .build();
    }

    public static List<BookCopyListDto> toListDTO(Set<BookCopy> bookCopies) {
        return bookCopies.stream()
                .map(BookCopyConverter::toListDTO)
                .collect(Collectors.toList());
    }

    public static BookCopy fromUpdateDTO(BookCopyUpdateDto bookCopyUpdateDto) {
        return BookCopy.builder()
                .id(bookCopyUpdateDto.getId())
                .status(bookCopyUpdateDto.getStatus())
                .registrationDate(bookCopyUpdateDto.getRegistrationDate())
                .pricePerDay(bookCopyUpdateDto.getPricePerDay())
                .imagePath(bookCopyUpdateDto.getImagePath())
                .build();
    }
}