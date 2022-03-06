package by.library.yurueu.service;

import by.library.yurueu.dto.UserDto;
import by.library.yurueu.dto.UserListDto;
import by.library.yurueu.dto.UserSaveDto;
import by.library.yurueu.dto.UserUpdateDto;
import by.library.yurueu.exception.ServiceException;

import java.util.Set;

public interface UserService {
    UserDto findById(Long id) throws ServiceException;
    Set<UserListDto> findAll() throws ServiceException;
    UserSaveDto add(UserSaveDto userSaveDto) throws ServiceException;
    boolean update(UserUpdateDto userUpdateDto) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
}