package by.library.yurueu.converter;

import by.library.yurueu.dto.RoleListDto;
import by.library.yurueu.dto.impl.RoleListDtoImpl;
import by.library.yurueu.entity.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleConverter {
    public static RoleListDto toListDTO(Role role) {
        return RoleListDtoImpl.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    public static List<RoleListDto> toListDTO(Set<Role> roles) {
        return roles.stream()
                .map(RoleConverter::toListDTO)
                .collect(Collectors.toList());
    }

    public static Role fromListDTO(RoleListDto roleListDto) {
        return Role.builder()
                .id(roleListDto.getId())
                .roleName(roleListDto.getRoleName())
                .build();
    }
}