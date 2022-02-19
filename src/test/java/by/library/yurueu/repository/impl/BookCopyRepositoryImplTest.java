package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.entity.BookCopyStatus;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class BookCopyRepositoryImplTest extends BaseRepositoryTest {

    private final BookCopyRepositoryImpl bookCopyRepository;

    public BookCopyRepositoryImplTest() {
        bookCopyRepository = new BookCopyRepositoryImpl(getDataSource());
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
    void findAllTest_shouldReturnListOfAllBookCopy() throws RepositoryException {
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
        BookCopy expected = BookCopy.builder().id(6L).status(BookCopyStatus.AVAILABLE).registrationDate(LocalDate.of(2000, 1, 1)).price(70).pricePerDay(13).bookId(2L).build();
        BookCopy actual = BookCopy.builder().status(BookCopyStatus.AVAILABLE).registrationDate(LocalDate.of(2000, 1, 1)).price(70).pricePerDay(13).bookId(2L).build();

        //when
        actual = bookCopyRepository.add(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, bookCopyRepository.findById(expected.getId()));
    }

    @Test
    void updateTest_shouldUpdateBookCopy() throws RepositoryException {
        //given
        BookCopy bookCopy = BookCopy.builder().id(2L).status(BookCopyStatus.AVAILABLE).registrationDate(LocalDate.of(2000, 1, 1)).price(70).pricePerDay(13).bookId(2L).build();

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
    }
}