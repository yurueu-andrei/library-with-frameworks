package by.library.yurueu.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class GenreUpdateDto {
    private Long id;
    private String genreName;
    private String deleteStatus;
}