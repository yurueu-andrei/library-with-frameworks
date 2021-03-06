package by.library.yurueu.controller;

import by.library.yurueu.dto.RoleDto;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/roles")
@PreAuthorize("hasAuthority({'ROLE_READ'})")
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public List<RoleDto> findAll() throws ServiceException {
        return roleService.findAll();
    }
}