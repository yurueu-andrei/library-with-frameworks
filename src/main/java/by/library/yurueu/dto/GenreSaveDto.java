package by.library.yurueu.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class GenreSaveDto {
    private Long id;
    private String genreName;
    private String deleteStatus;
}