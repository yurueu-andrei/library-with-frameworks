package by.library.yurueu.mapper;

import by.library.yurueu.dto.RoleDto;
import by.library.yurueu.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toListDTO(Role role);

    List<RoleDto> toListDto(List<Role> roles);
}