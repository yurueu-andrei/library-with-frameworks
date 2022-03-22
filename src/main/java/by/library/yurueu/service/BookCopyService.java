package by.library.yurueu.service;

import by.library.yurueu.dto.BookCopyDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookCopySaveDto;
import by.library.yurueu.dto.BookCopyUpdateDto;
import by.library.yurueu.exception.ServiceException;

import java.util.List;

public interface BookCopyService {
    BookCopyDto findById(Long id) throws ServiceException;
    List<BookCopyListDto> findAll() throws ServiceException;
    BookCopySaveDto add(BookCopySaveDto bookCopySaveDto) throws ServiceException;
    boolean update(BookCopyUpdateDto bookCopyUpdateDto) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
}