package by.library.yurueu.service;

import by.library.yurueu.dto.GenreListDto;
import by.library.yurueu.dto.GenreUpdateDto;
import by.library.yurueu.exception.ServiceException;

import java.util.Set;

public interface GenreService {
    GenreListDto findById(Long id) throws ServiceException;
    Set<GenreListDto> findAll() throws ServiceException;
    GenreListDto add(GenreListDto genreListDto) throws ServiceException;
    boolean update(GenreUpdateDto genreUpdateDto) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
}