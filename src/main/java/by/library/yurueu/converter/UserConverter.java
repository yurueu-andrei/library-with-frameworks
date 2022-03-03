package by.library.yurueu.converter;

import by.library.yurueu.dto.UserDto;
import by.library.yurueu.dto.UserListDto;
import by.library.yurueu.dto.UserSaveDto;
import by.library.yurueu.dto.impl.UserDtoImpl;
import by.library.yurueu.dto.impl.UserListDtoImpl;
import by.library.yurueu.dto.impl.UserSaveDtoImpl;
import by.library.yurueu.entity.BaseEntity;
import by.library.yurueu.entity.Role;
import by.library.yurueu.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserConverter {
    public static UserDto toDTO(User user) {
        return UserDtoImpl.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .passportNumber(user.getPassportNumber())
                .email(user.getEmail())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .rolesId(constructRolesId(user.getRoles()))
                .orders(OrderConverter.toListDTO(user.getOrders()))
                .build();
    }

    private static Set<Long> constructRolesId(Set<Role> roles) {
        return roles.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
    }

    public static UserSaveDto toSaveDTO(User user) {
        return UserSaveDtoImpl.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .passportNumber(user.getPassportNumber())
                .email(user.getEmail())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .rolesId(constructRolesId(user.getRoles()))
                .build();
    }

    public static User fromSaveDTO(UserSaveDto userSaveDto) {
        return User.builder()
                .id(userSaveDto.getId())
                .firstName(userSaveDto.getFirstName())
                .lastName(userSaveDto.getLastName())
                .passportNumber(userSaveDto.getPassportNumber())
                .email(userSaveDto.getEmail())
                .address(userSaveDto.getAddress())
                .birthDate(userSaveDto.getBirthDate())
                .roles(constructBookCopies(userSaveDto.getRolesId()))
                .build();
    }

    private static Set<Role> constructBookCopies(Set<Long> rolesId) {
        return rolesId.stream()
                .map(roleId -> Role.builder().id(roleId).build())
                .collect(Collectors.toSet());
    }

    public static UserListDto toListDTO(User user) {
        return UserListDtoImpl.builder()
                .id(user.getId())
                .email(user.getEmail())
                .address(user.getAddress())
                .build();
    }
}