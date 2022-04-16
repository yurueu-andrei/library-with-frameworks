package by.library.yurueu.service;

import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveAndUpdateDto;
import by.library.yurueu.exception.ServiceException;

import java.util.List;

public interface AuthorService {
    AuthorDto findById(Long id) throws ServiceException;
    List<AuthorListDto> findAll() throws ServiceException;
    AuthorSaveAndUpdateDto add(AuthorSaveAndUpdateDto authorSaveAndUpdateDto) throws ServiceException;
    boolean update(AuthorSaveAndUpdateDto authorUpdateDto) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
}