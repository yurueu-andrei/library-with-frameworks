package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.BaseRepositoryTest;
import by.library.yurueu.repository.BookCopyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class BookCopyRepositoryImplTest extends BaseRepositoryTest {
    private final BookCopyRepository bookCopyRepository;

    public BookCopyRepositoryImplTest() {
        bookCopyRepository = new BookCopyRepositoryImpl();
    }

    @Test
    public void findByIdTest_shouldReturnTheFirstBookCopyInDB() throws RepositoryException {
        //given
        BookCopy expected = findBookCopyForFindById();

        //when
        BookCopy actual = bookCopyRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllBookCopiesList() throws RepositoryException {
        //given
        List<BookCopy> expected = findBookCopiesForFindAll();

        //when
        List<BookCopy> actual = bookCopyRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedBookCopy() throws RepositoryException {
        //given
        BookCopy expected = BookCopy.builder().id(6L).status("AVAILABLE").registrationDate(LocalDate.of(2000, 1, 1)).imagePath("image path").pricePerDay(13).book(Book.builder().id(1L).build()).build();
        BookCopy actual = BookCopy.builder().status("AVAILABLE").registrationDate(LocalDate.of(2000, 1, 1)).imagePath("image path").pricePerDay(13).book(Book.builder().id(1L).build()).build();

        //when
        actual = bookCopyRepository.add(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, bookCopyRepository.findById(expected.getId()));
    }

    @Test
    void updateTest_shouldUpdateBookCopy() throws RepositoryException {
        //given
        BookCopy bookCopy = BookCopy.builder().id(2L).status("AVAILABLE").registrationDate(LocalDate.of(2000, 1, 1)).imagePath("image path").pricePerDay(13).build();

        // when
        boolean isUpdated = bookCopyRepository.update(bookCopy);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(bookCopy, bookCopyRepository.findById(bookCopy.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteBookCopy() throws RepositoryException {
        //given
        Long bookCopyId = 1L;

        // when
        boolean isDeleted = bookCopyRepository.delete(bookCopyId);

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertThrows(RepositoryException.class, () -> bookCopyRepository.findById(bookCopyId));
    }
}