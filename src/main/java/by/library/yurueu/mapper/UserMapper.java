package by.library.yurueu.mapper;

import by.library.yurueu.dto.UserDto;
import by.library.yurueu.dto.UserListDto;
import by.library.yurueu.dto.UserSaveDto;
import by.library.yurueu.dto.UserUpdateDto;
import by.library.yurueu.entity.Role;
import by.library.yurueu.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "rolesId", source = "user.roles")
    UserDto toDTO(User user);

    @Mapping(target = "rolesId", source = "user.roles")
    UserSaveDto toSaveDTO(User user);

    @Mapping(target = "roles", source = "userSaveDto.rolesId")
    User fromSaveDTO(UserSaveDto userSaveDto);

    User fromUpdateDTO(UserUpdateDto userUpdateDto);

    UserListDto toListDTO(User user);

    List<UserListDto> toListDto(List<User> users);

    default Set<Role> fromLongsList(List<Long> value) {
        return value.stream().map(roleId -> Role.builder().id(roleId).build())
                .collect(Collectors.toSet());
    }

    default List<Long> toLongsList(Set<Role> roles) {
        return roles.stream().map(Role::getId)
                .collect(Collectors.toList());
    }
}