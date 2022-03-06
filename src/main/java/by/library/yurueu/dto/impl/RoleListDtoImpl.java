package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.RoleListDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class RoleListDtoImpl implements RoleListDto {
    private Long id;
    private String roleName;
}