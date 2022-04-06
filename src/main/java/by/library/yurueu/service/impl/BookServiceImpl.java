package by.library.yurueu.service.impl;

import by.library.yurueu.converter.BookConverter;
import by.library.yurueu.dto.BookSaveDto;
import by.library.yurueu.entity.Book;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.BookRepository;
import by.library.yurueu.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Transactional
    @Override
    public BookSaveDto add(BookSaveDto bookSaveDto) throws ServiceException {
        try {
            Book book = BookConverter.fromSaveDTO(bookSaveDto);
            return BookConverter.toSaveDTO(bookRepository.save(book));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not added"));
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        Optional<Book> book = bookRepository.findById(id);
        bookRepository.delete(book.orElseThrow(
                () -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not deleted")))
        );
        return true;
    }
}