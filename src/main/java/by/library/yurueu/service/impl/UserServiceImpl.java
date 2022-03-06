package by.library.yurueu.service.impl;

import by.library.yurueu.converter.UserConverter;
import by.library.yurueu.dto.UserDto;
import by.library.yurueu.dto.UserListDto;
import by.library.yurueu.dto.UserSaveDto;
import by.library.yurueu.dto.UserUpdateDto;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.UserRepository;
import by.library.yurueu.repository.impl.UserRepositoryImpl;
import by.library.yurueu.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl() {
        userRepository = new UserRepositoryImpl();
    }

    @Override
    public UserDto findById(Long id) throws ServiceException {
        try {
            User user = userRepository.findById(id);
            user.setRoles(userRepository.findRolesByUserId(id));
            user.setOrders(userRepository.findOrdersByUserId(id));
            return UserConverter.toDto(user);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public Set<UserListDto> findAll() throws ServiceException {
        try {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(UserConverter::toListDto)
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public UserSaveDto add(UserSaveDto userSaveDto) throws ServiceException {
        try {
            User user = UserConverter.fromSaveDto(userSaveDto);
            user.setRoles(userRepository.findRolesByRolesId(userSaveDto.getRolesId()));
            return UserConverter.toSaveDto(userRepository.add(user));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean update(UserUpdateDto userUpdateDto) throws ServiceException {
        try {
            User user = UserConverter.fromUpdateDto(userUpdateDto);
            return userRepository.update(user);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return userRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }
}