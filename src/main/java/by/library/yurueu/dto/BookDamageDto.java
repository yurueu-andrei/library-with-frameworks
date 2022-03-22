package by.library.yurueu.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class BookDamageDto {
    private Long id;
    private String imagePath;
    private String damageDescription;
    private Long userId;
    private Long orderId;
    private Long bookCopyId;
}
