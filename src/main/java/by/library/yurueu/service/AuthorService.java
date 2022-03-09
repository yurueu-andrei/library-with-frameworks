package by.library.yurueu.service;

import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveDto;
import by.library.yurueu.dto.AuthorUpdateDto;
import by.library.yurueu.exception.ServiceException;

import java.util.List;

public interface AuthorService {
    AuthorDto findById(Long id) throws ServiceException;
    List<AuthorListDto> findAll() throws ServiceException;
    AuthorSaveDto add(AuthorSaveDto authorSaveDto) throws ServiceException;
    boolean update(AuthorUpdateDto authorUpdateDto) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
}