package by.library.yurueu.service.impl;

import by.library.yurueu.converter.BookDamageConverter;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.BookDamageSaveDto;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.BookDamageRepository;
import by.library.yurueu.repository.impl.BookDamageRepositoryImpl;
import by.library.yurueu.service.BookDamageService;

import java.util.HashSet;
import java.util.List;

public class BookDamageServiceImpl implements BookDamageService {
    private final BookDamageRepository bookDamageRepository;

    public BookDamageServiceImpl() {
        bookDamageRepository = new BookDamageRepositoryImpl();
    }

    @Override
    public List<BookDamageListDto> findAll() throws ServiceException {
        try {
            List<BookDamage> bookDamages = bookDamageRepository.findAll();
            return BookDamageConverter.toListDTO(new HashSet<>(bookDamages));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public BookDamageSaveDto add(BookDamageSaveDto bookDamageSaveDto) throws ServiceException {
        try {
            BookDamage bookDamage = BookDamageConverter.fromSaveDTO(bookDamageSaveDto);
            bookDamage.setUser(User.builder().id(bookDamageSaveDto.getUserId()).build());
            bookDamage.setOrder(Order.builder().id(bookDamageSaveDto.getOrderId()).build());
            bookDamage.setBookCopy(BookCopy.builder().id(bookDamageSaveDto.getBookCopyId()).build());
            return BookDamageConverter.toSaveDTO(bookDamageRepository.add(bookDamage));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return bookDamageRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }
}