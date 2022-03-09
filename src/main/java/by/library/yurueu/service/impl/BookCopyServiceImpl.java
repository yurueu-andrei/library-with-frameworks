package by.library.yurueu.service.impl;

import by.library.yurueu.converter.BookCopyConverter;
import by.library.yurueu.dto.BookCopyDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookCopySaveDto;
import by.library.yurueu.dto.BookCopyUpdateDto;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.BookCopyRepository;
import by.library.yurueu.repository.BookRepository;
import by.library.yurueu.repository.impl.BookCopyRepositoryImpl;
import by.library.yurueu.repository.impl.BookRepositoryImpl;
import by.library.yurueu.service.BookCopyService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookCopyServiceImpl implements BookCopyService {
    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;

    public BookCopyServiceImpl() {
        bookCopyRepository = new BookCopyRepositoryImpl();
        bookRepository = new BookRepositoryImpl();
    }

    @Override
    public BookCopyDto findById(Long id) throws ServiceException {
        try {
            BookCopy bookCopy = bookCopyRepository.findById(id);
            bookCopy.setBook(bookCopyRepository.findBookByBookCopyId(id));
            bookCopy.setBookDamages(bookCopyRepository.findBookDamagesByBookCopyId(id));
            bookCopy.setOrders(bookCopyRepository.findOrdersByBookCopyId(id));
            return BookCopyConverter.toDTO(bookCopy);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public Set<BookCopyListDto> findAll() throws ServiceException {
        try {
            List<BookCopy> bookCopies = bookCopyRepository.findAll();
            return bookCopies.stream()
                    .map(BookCopyConverter::toListDTO)
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public BookCopySaveDto add(BookCopySaveDto bookCopySaveDto) throws ServiceException {
        try {
            BookCopy bookCopy = BookCopyConverter.fromSaveDTO(bookCopySaveDto);
            bookCopy.setBook(Book.builder().id(bookCopySaveDto.getBookId()).build());
            return BookCopyConverter.toSaveDTO(bookCopyRepository.add(bookCopy));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean update(BookCopyUpdateDto bookCopyUpdateDto) throws ServiceException {
        try {
            BookCopy bookCopy = BookCopyConverter.fromUpdateDTO(bookCopyUpdateDto);
            return bookCopyRepository.update(bookCopy);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return bookCopyRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }
}