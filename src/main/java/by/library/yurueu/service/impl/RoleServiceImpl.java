package by.library.yurueu.service.impl;

import by.library.yurueu.converter.RoleConverter;
import by.library.yurueu.dto.RoleListDto;
import by.library.yurueu.entity.Role;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.RoleRepository;
import by.library.yurueu.repository.impl.RoleRepositoryImpl;
import by.library.yurueu.service.RoleService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl() {
        roleRepository = new RoleRepositoryImpl();
    }

    @Override
    public Set<RoleListDto> findAll() throws ServiceException {
        try {
            List<Role> roles = roleRepository.findAll();
            return roles.stream()
                    .map(RoleConverter::toListDTO)
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public RoleListDto add(RoleListDto roleListDto) throws ServiceException {
        try {
            Role role = RoleConverter.fromListDTO(roleListDto);
            return RoleConverter.toListDTO(roleRepository.add(role));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean update(RoleListDto roleListDto) throws ServiceException {
        try {
            Role role = RoleConverter.fromListDTO(roleListDto);
            return roleRepository.update(role);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return roleRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }
}