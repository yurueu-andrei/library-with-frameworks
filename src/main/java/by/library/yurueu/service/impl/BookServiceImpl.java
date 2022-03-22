package by.library.yurueu.service.impl;

import by.library.yurueu.converter.BookConverter;
import by.library.yurueu.dto.BookSaveDto;
import by.library.yurueu.entity.Book;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.BookRepository;
import by.library.yurueu.service.BookService;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;

@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public BookSaveDto add(BookSaveDto bookSaveDto) throws ServiceException {
        try {
            Book book = BookConverter.fromSaveDTO(bookSaveDto);
            book.setGenres(bookRepository.findGenresByGenresId(new HashSet<>(bookSaveDto.getGenresId())));
            book.setAuthors(bookRepository.findAuthorsByAuthorsId(new HashSet<>(bookSaveDto.getAuthorsId())));
            return BookConverter.toSaveDTO(bookRepository.add(book));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return bookRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }
}