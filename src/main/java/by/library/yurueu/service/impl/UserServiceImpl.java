package by.library.yurueu.service.impl;

import by.library.yurueu.dto.UserDto;
import by.library.yurueu.dto.UserListDto;
import by.library.yurueu.dto.UserSaveDto;
import by.library.yurueu.dto.UserUpdateDto;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.mapper.UserMapper;
import by.library.yurueu.repository.BookDamageRepository;
import by.library.yurueu.repository.OrderRepository;
import by.library.yurueu.repository.UserRepository;
import by.library.yurueu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final BookDamageRepository bookDamageRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDto findById(Long id) throws ServiceException {
        return userRepository.findById(id).map(userMapper::toDTO)
                .orElseThrow(() -> new ServiceException(String.format("The user was not found. id = %d", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserListDto> findAll() throws ServiceException {
        try {
            return userMapper.toListDto(userRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException("The users were not found", ex);
        }
    }

    @Transactional
    @Override
    public UserSaveDto add(UserSaveDto userSaveDto) throws ServiceException {
        try {
            User user = userMapper.fromSaveDTO(userSaveDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setStatus("ACTIVE");
            return userMapper.toSaveDTO(userRepository.save(user));
        } catch (Exception ex) {
            throw new ServiceException(String.format("The user was not saved. %s", userSaveDto), ex);
        }
    }

    @Transactional
    @Override
    public boolean update(UserUpdateDto userUpdateDto) throws ServiceException {
        User user = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(()->
                        new ServiceException(
                                String.format(
                                        "The user was not updated. The user was not found. id = %d",
                                        userUpdateDto.getId())));
        try {
            settingUpdatedFields(user, userUpdateDto);
            userRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The user was not updated. %s", userUpdateDto), ex);
        }
    }

    private void settingUpdatedFields(User user, UserUpdateDto userUpdateDto) {
        if (userUpdateDto.getFirstName() != null) {
            user.setFirstName(userUpdateDto.getFirstName());
        }
        if (userUpdateDto.getLastName() != null) {
            user.setLastName(userUpdateDto.getLastName());
        }
        if (userUpdateDto.getPassportNumber() != null) {
            user.setPassportNumber(userUpdateDto.getPassportNumber());
        }
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getPassword() != null) {
            user.setPassword(userUpdateDto.getPassword());
        }
        if (userUpdateDto.getAddress() != null) {
            user.setAddress(userUpdateDto.getAddress());
        }
        if (userUpdateDto.getBirthDate() != null) {
            user.setBirthDate(userUpdateDto.getBirthDate());
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The user was not deleted. The user was not found. id = %d", id)));
        try {
            deleteLinks(user);
            user.setStatus("DELETED");
            userRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The user was not deleted. id = %d", id), ex);
        }
    }

    private void deleteLinks(User user) {
        user.getOrders().forEach(order -> {
            order.setStatus("DELETED");
            orderRepository.save(order);
        });

        user.getBookDamages().forEach(bookDamage -> {
            bookDamage.setStatus("DELETED");
            bookDamageRepository.save(bookDamage);
        });
    }
}