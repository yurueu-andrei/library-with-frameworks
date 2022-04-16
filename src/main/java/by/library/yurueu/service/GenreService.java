package by.library.yurueu.service;

import by.library.yurueu.dto.GenreDto;
import by.library.yurueu.exception.ServiceException;

import java.util.List;

public interface GenreService {
    GenreDto findById(Long id) throws ServiceException;
    List<GenreDto> findAll() throws ServiceException;
}