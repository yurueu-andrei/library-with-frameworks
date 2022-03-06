package by.library.yurueu.dto.impl;

import by.library.yurueu.dto.BookDamageSaveDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class BookDamageSaveDtoImpl implements BookDamageSaveDto {
    private Long id;
    private String imagePath;
    private String damageDescription;
    private Long userId;
    private Long orderId;
    private Long bookCopyId;
}