package by.library.yurueu.service;

import by.library.yurueu.dto.BookSaveDto;
import by.library.yurueu.exception.ServiceException;

public interface BookService {
    BookSaveDto add(BookSaveDto bookSaveDto) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
}