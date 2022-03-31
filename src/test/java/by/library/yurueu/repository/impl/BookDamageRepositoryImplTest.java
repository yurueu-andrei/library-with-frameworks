package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookDamage;
import by.library.yurueu.entity.Order;
import by.library.yurueu.entity.User;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.BaseRepositoryTest;
import by.library.yurueu.repository.BookDamageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookDamageRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private BookDamageRepository bookDamageRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstBookDamageInDB() throws RepositoryException {
        //given
        BookDamage expected = findBookDamageForFindById();

        //when
        BookDamage actual = bookDamageRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllBookDamagesList() throws RepositoryException {
        //given
        List<BookDamage> expected = findBookDamageForFindAll();

        //when
        List<BookDamage> actual = bookDamageRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedBookDamage() throws RepositoryException {
        //given
        BookDamage expected = BookDamage.builder().id(5L).imagePath("image path").damageDescription("damage5").bookCopy(BookCopy.builder().id(3L).build()).order(Order.builder().id(3L).build()).user(User.builder().id(3L).build()).build();
        BookDamage actual = BookDamage.builder().imagePath("image path").damageDescription("damage5").bookCopy(BookCopy.builder().id(3L).build()).order(Order.builder().id(3L).build()).user(User.builder().id(3L).build()).build();

        //when
        actual = bookDamageRepository.add(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, bookDamageRepository.findById(expected.getId()));
    }

    @Test
    void updateTest_shouldUpdateBookDamage() throws RepositoryException {
        //given
        BookDamage bookDamage = BookDamage.builder().id(2L).imagePath("image path").damageDescription("damage3").build();

        // when
        boolean isUpdated = bookDamageRepository.update(bookDamage);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(bookDamage, bookDamageRepository.findById(bookDamage.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteBookDamage() throws RepositoryException {
        //given
        Long bookDamageId = 1L;

        // when
        boolean isDeleted = bookDamageRepository.delete(bookDamageId);

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertThrows(RepositoryException.class, () -> bookDamageRepository.findById(bookDamageId));
    }
}