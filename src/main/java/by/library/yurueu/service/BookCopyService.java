package by.library.yurueu.service;

import by.library.yurueu.dto.BookCopyDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookCopySaveAndUpdateDto;
import by.library.yurueu.exception.ServiceException;

import java.util.List;

public interface BookCopyService {
    BookCopyDto findById(Long id) throws ServiceException;
    List<BookCopyListDto> findAll() throws ServiceException;
    BookCopySaveAndUpdateDto add(BookCopySaveAndUpdateDto bookCopySaveAndUpdateDto) throws ServiceException;
    boolean update(BookCopySaveAndUpdateDto bookCopyUpdateDto) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
}