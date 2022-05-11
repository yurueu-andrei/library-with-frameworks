package by.library.yurueu.service.impl;

import by.library.yurueu.dto.RoleDto;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.mapper.RoleMapper;
import by.library.yurueu.repository.RoleRepository;
import by.library.yurueu.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional(readOnly = true)
    @Override
    public List<RoleDto> findAll() throws ServiceException {
        try {
            return roleMapper.toListDto(roleRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException("The roles were not found", ex);
        }
    }
}