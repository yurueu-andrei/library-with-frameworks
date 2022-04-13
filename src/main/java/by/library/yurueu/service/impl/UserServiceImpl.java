package by.library.yurueu.service.impl;

import by.library.yurueu.converter.UserConverter;
import by.library.yurueu.dto.UserDto;
import by.library.yurueu.dto.UserListDto;
import by.library.yurueu.dto.UserSaveDto;
import by.library.yurueu.dto.UserUpdateDto;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.BookDamageRepository;
import by.library.yurueu.repository.OrderRepository;
import by.library.yurueu.repository.UserRepository;
import by.library.yurueu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final BookDamageRepository bookDamageRepository;

    @Override
    public UserDto findById(Long id) throws ServiceException {
        return userRepository.findById(id).map(UserConverter::toDTO)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserListDto> findAll() throws ServiceException {
        try {
            return UserConverter.toListDTO(userRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName() + "s", "were not found"));
        }
    }

    @Transactional
    @Override
    public UserSaveDto add(UserSaveDto userSaveDto) throws ServiceException {
        try {
            User user = UserConverter.fromSaveDTO(userSaveDto);
            user.setStatus("ACTIVE");
            userRepository.save(user);
            return UserConverter.toSaveDTO(user);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not added"));
        }
    }

    @Transactional
    @Override
    public boolean update(UserUpdateDto userUpdateDto) throws ServiceException {
        try {
            User user = UserConverter.fromUpdateDTO(userUpdateDto);
            user.setStatus("ACTIVE");
            userRepository.save(user);
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not updated"));
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        Optional<User> userToDelete = userRepository.findById(id);
        if (userToDelete.isPresent()) {
            User user = userToDelete.get();
            deleteLinks(user);
            user.setStatus("DELETED");
            userRepository.save(user);
            return true;
        }
        throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not deleted"));
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