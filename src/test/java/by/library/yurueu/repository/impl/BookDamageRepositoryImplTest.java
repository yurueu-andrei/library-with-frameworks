package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;
import by.library.yurueu.repository.BaseRepositoryTest;
import by.library.yurueu.repository.BookDamageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class BookDamageRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private BookDamageRepository bookDamageRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstBookDamageInDB() {
        //given
        BookDamage expected = findBookDamageForFindById();

        //when
        Optional<BookDamage> bookDamage = bookDamageRepository.findById(expected.getId());
        BookDamage actual = bookDamage.orElse(BookDamage.builder().build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllBookDamagesList() {
        //given
        List<BookDamage> expected = findBookDamageForFindAll();

        //when
        List<BookDamage> actual = bookDamageRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedBookDamage() {
        //given
        BookDamage expected = BookDamage.builder().id(5L).imagePath("image path").damageDescription("damage5").bookCopy(BookCopy.builder().id(3L).build()).order(Order.builder().id(3L).build()).user(User.builder().id(3L).build()).build();
        BookDamage actual = BookDamage.builder().imagePath("image path").damageDescription("damage5").bookCopy(BookCopy.builder().id(3L).build()).order(Order.builder().id(3L).build()).user(User.builder().id(3L).build()).build();

        //when
        actual = bookDamageRepository.saveAndFlush(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, bookDamageRepository.findById(expected.getId()).get());
    }

    @Test
    void updateTest_shouldUpdateBookDamage() {
        //given
        BookDamage bookDamage = BookDamage.builder().id(2L).imagePath("image path").damageDescription("damage3").bookCopy(BookCopy.builder().id(1L).build()).user(User.builder().id(1L).build()).order(Order.builder().id(1L).build()).build();

        // when
        bookDamageRepository.saveAndFlush(bookDamage);
        Optional<BookDamage> foundBookDamage = bookDamageRepository.findById(bookDamage.getId());

        //then
        Assertions.assertTrue(foundBookDamage.isPresent());
        Assertions.assertEquals(bookDamage, bookDamageRepository.findById(bookDamage.getId()).get());
    }
}