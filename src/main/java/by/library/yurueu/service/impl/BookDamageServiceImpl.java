package by.library.yurueu.service.impl;

import by.library.yurueu.converter.BookDamageConverter;
import by.library.yurueu.dto.BookDamageDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.BookDamageSaveDto;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.BookDamageRepository;
import by.library.yurueu.service.BookDamageService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookDamageServiceImpl implements BookDamageService {
    private final BookDamageRepository bookDamageRepository;

    @Override
    public BookDamageDto findById(Long id) throws ServiceException {
        try {
            BookDamage bookDamage = bookDamageRepository.findById(id);
            bookDamage.setBookCopy(BookCopy.builder().id(bookDamage.getBookCopy().getId()).build());
            bookDamage.setUser(User.builder().id(bookDamage.getUser().getId()).build());
            bookDamage.setOrder(Order.builder().id(bookDamage.getOrder().getId()).build());
            return BookDamageConverter.toDTO(bookDamage);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public List<BookDamageListDto> findAll() throws ServiceException {
        try {
            List<BookDamage> bookDamages = bookDamageRepository.findAll();
            return BookDamageConverter.toListDTO(bookDamages);
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