package by.library.yurueu.mapper;

import by.library.yurueu.dto.BookDamageDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.entity.BookDamage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookDamageMapper {
    @Mapping(target = "bookCopyId", source = "bookDamage.bookCopy.id")
    @Mapping(target = "orderId", source = "bookDamage.order.id")
    @Mapping(target = "userId", source = "bookDamage.user.id")
    BookDamageDto toDTO(BookDamage bookDamage);

    @Mapping(target = "bookCopyId", source = "bookDamage.bookCopy.id")
    @Mapping(target = "orderId", source = "bookDamage.order.id")
    @Mapping(target = "userId", source = "bookDamage.user.id")
    BookDamageDto toSaveDTO(BookDamage bookDamage);

    @Mapping(target = "bookCopy.id", source = "bookDamageDto.bookCopyId")
    @Mapping(target = "order.id", source = "bookDamageDto.orderId")
    @Mapping(target = "user.id", source = "bookDamageDto.userId")
    BookDamage fromSaveDTO(BookDamageDto bookDamageDto);

    BookDamageListDto toListDTO(BookDamage bookDamage);
    List<BookDamageListDto> toListDto(List<BookDamage> bookDamages);
}