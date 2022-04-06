package by.library.yurueu.converter;

import by.library.yurueu.dto.RoleDto;
import by.library.yurueu.entity.Role;

import java.util.List;
import java.util.stream.Collectors;

public class RoleConverter {
    public static RoleDto toListDTO(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    public static List<RoleDto> toListDTO(List<Role> roles) {
        return roles.stream()
                .map(RoleConverter::toListDTO)
                .collect(Collectors.toList());
    }

//    public static Role fromListDTO(RoleDto roleListDto) {
//        return Role.builder()
//                .id(roleListDto.getId())
//                .roleName(roleListDto.getRoleName())
//                .build();
//    }
}