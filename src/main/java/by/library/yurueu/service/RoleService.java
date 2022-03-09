package by.library.yurueu.service;

import by.library.yurueu.dto.RoleListDto;
import by.library.yurueu.exception.ServiceException;

import java.util.Set;

public interface RoleService {
    Set<RoleListDto> findAll() throws ServiceException;
    RoleListDto add(RoleListDto roleListDto) throws ServiceException;
    boolean update(RoleListDto userUpdateDto) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
}