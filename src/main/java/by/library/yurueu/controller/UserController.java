package by.library.yurueu.controller;

import by.library.yurueu.dto.UserDto;
import by.library.yurueu.dto.UserListDto;
import by.library.yurueu.dto.UserSaveDto;
import by.library.yurueu.dto.UserUpdateDto;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole({'admin'})")
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) throws ServiceException {
        return userService.findById(id);
    }

    @PreAuthorize("hasRole({'admin'})")
    @GetMapping
    public List<UserListDto> findAll() throws ServiceException {
        return userService.findAll();
    }

    @PostMapping
    public UserSaveDto add(
            @RequestBody UserSaveDto userSaveDto
    ) throws ServiceException {
        return userService.add(userSaveDto);
    }

    @PreAuthorize("hasAnyRole('admin', 'user')")
    @PutMapping
    public UserUpdateDto update(
            @RequestBody UserUpdateDto userUpdateDto
    ) throws ServiceException {
        userService.update(userUpdateDto);
        return userUpdateDto;
    }

    @PreAuthorize("hasAnyRole('admin', 'user')")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return userService.delete(id);
    }
}