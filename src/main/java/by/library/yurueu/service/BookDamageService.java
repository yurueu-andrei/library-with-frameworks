package by.library.yurueu.service;

import by.library.yurueu.dto.BookDamageDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.BookDamageSaveDto;
import by.library.yurueu.exception.ServiceException;

import java.util.List;

public interface BookDamageService {
    BookDamageDto findById(Long id) throws ServiceException;
    List<BookDamageListDto> findAll() throws ServiceException;
    BookDamageSaveDto add(BookDamageSaveDto bookDamageSaveDto) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
}