package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.BookDamageListDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class BookDamageListDtoImpl implements BookDamageListDto {
    private Long id;
}