package by.library.yurueu.mapper;

import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveAndUpdateDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = BookCopyMapper.class)
public interface AuthorMapper {
    @Mapping(target = "books", source = "author.books")
    AuthorDto toDTO(Author author);
    default List<BookCopy> bookCopiesFromBooks(Set<Book> books) {
        List<BookCopy> bookCopies = new ArrayList<>();
        books.forEach(book -> bookCopies.addAll(book.getBookCopies()));
        return bookCopies;
    }
    AuthorSaveAndUpdateDto toSaveDTO(Author author);
    Author fromSaveOrUpdateDTO(AuthorSaveAndUpdateDto authorSaveAndUpdateDto);
    AuthorListDto toListDTO(Author author);
    List<AuthorListDto> toListDto(List<Author> authors);
}