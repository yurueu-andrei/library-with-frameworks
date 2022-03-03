package by.library.yurueu.converter;

import by.library.yurueu.dto.RoleListDto;
import by.library.yurueu.dto.impl.RoleListDtoImpl;
import by.library.yurueu.entity.Role;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleConverter {
    public static RoleListDto toListDTO(Role role) {
        return RoleListDtoImpl.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    public static Set<RoleListDto> toListDTO(Set<Role> roles) {
        return roles.stream()
                .map(RoleConverter::toListDTO)
                .collect(Collectors.toSet());
    }
}