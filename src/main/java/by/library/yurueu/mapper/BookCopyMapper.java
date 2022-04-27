package by.library.yurueu.mapper;

import by.library.yurueu.dto.BookCopyDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookCopySaveAndUpdateDto;
import by.library.yurueu.entity.BookCopy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    @Mapping(target = "bookDamages", source = "bookCopy.bookDamages")
    @Mapping(target = "genres", source = "bookCopy.book.genres")
    @Mapping(target = "authors", source = "bookCopy.book.authors")
    BookCopyDto toDTO(BookCopy bookCopy);

    @Mapping(target = "bookId", source = "bookCopy.book.id")
    BookCopySaveAndUpdateDto toSaveDTO(BookCopy bookCopy);

    @Mapping(target = "book.id", source = "bookCopySaveAndUpdateDto.bookId")
    BookCopy fromSaveOrUpdateDTO(BookCopySaveAndUpdateDto bookCopySaveAndUpdateDto);

    @Mapping(target = "title", source = "bookCopy.book.title")
    BookCopyListDto toListDTO(BookCopy bookCopy);

    List<BookCopyListDto> toListDto(List<BookCopy> bookCopies);
}