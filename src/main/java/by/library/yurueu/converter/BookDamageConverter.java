package by.library.yurueu.converter;

import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.BookDamageSaveDto;
import by.library.yurueu.dto.impl.BookDamageListDtoImpl;
import by.library.yurueu.dto.impl.BookDamageSaveDtoImpl;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public class BookDamageConverter {
    public static BookDamageSaveDto toSaveDTO(BookDamage bookDamage) {
        return BookDamageSaveDtoImpl.builder()
                .id(bookDamage.getId())
                .imagePath(bookDamage.getImagePath())
                .damageDescription(bookDamage.getDamageDescription())
                .userId(bookDamage.getUser().getId())
                .orderId(bookDamage.getOrder().getId())
                .bookCopyId(bookDamage.getBookCopy().getId())
                .build();
    }

    public static BookDamage fromSaveDTO(BookDamageSaveDto bookDamageSaveDto) {
        return BookDamage.builder()
                .id(bookDamageSaveDto.getId())
                .imagePath(bookDamageSaveDto.getImagePath())
                .damageDescription(bookDamageSaveDto.getDamageDescription())
                .user(User.builder().id(bookDamageSaveDto.getUserId()).build())
                .order(Order.builder().id(bookDamageSaveDto.getOrderId()).build())
                .bookCopy(BookCopy.builder().id(bookDamageSaveDto.getBookCopyId()).build())
                .build();
    }

    public static BookDamageListDto toListDTO(BookDamage bookDamage) {
        return BookDamageListDtoImpl.builder()
                .id(bookDamage.getId())
                .build();
    }

    public static Set<BookDamageListDto> toListDTO(Set<BookDamage> bookDamages) {
        return bookDamages.stream()
                .map(BookDamageConverter::toListDTO)
                .collect(Collectors.toSet());
    }
}