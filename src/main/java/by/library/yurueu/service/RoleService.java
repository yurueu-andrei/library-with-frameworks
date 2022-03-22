package by.library.yurueu.service;

import by.library.yurueu.dto.RoleDto;
import by.library.yurueu.exception.ServiceException;

import java.util.List;

public interface RoleService {
    List<RoleDto> findAll() throws ServiceException;
}