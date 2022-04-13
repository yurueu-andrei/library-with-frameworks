package by.library.yurueu.converter;

import by.library.yurueu.dto.BookDamageDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.BookDamageSaveDto;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class BookDamageConverter {
    public static BookDamageDto toDTO(BookDamage bookDamage) {
        return BookDamageDto.builder()
                .id(bookDamage.getId())
                .damageDescription(bookDamage.getDamageDescription())
                .imagePath(bookDamage.getImagePath())
                .bookCopyId(bookDamage.getBookCopy().getId())
                .orderId(bookDamage.getOrder().getId())
                .userId(bookDamage.getUser().getId())
                .build();
    }

    public static BookDamageSaveDto toSaveDTO(BookDamage bookDamage) {
        return BookDamageSaveDto.builder()
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
        return BookDamageListDto.builder()
                .id(bookDamage.getId())
                .build();
    }

    public static List<BookDamageListDto> toListDTO(List<BookDamage> bookDamages) {
        return bookDamages.stream()
                .map(BookDamageConverter::toListDTO)
                .collect(Collectors.toList());
    }
}