package by.library.yurueu.service.impl;

import by.library.yurueu.converter.RoleConverter;
import by.library.yurueu.dto.RoleDto;
import by.library.yurueu.entity.Role;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.RoleRepository;
import by.library.yurueu.service.RoleService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<RoleDto> findAll() throws ServiceException {
        try {
            List<Role> roles = roleRepository.findAll();
            return RoleConverter.toListDTO(roles);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }
}