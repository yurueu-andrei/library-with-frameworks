package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.repository.BaseRepositoryTest;
import by.library.yurueu.repository.BookCopyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class BookCopyRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstBookCopyInDB() {
        //given
        BookCopy expected = findBookCopyForFindById();

        //when
        Optional<BookCopy> bookCopy = bookCopyRepository.findById(expected.getId());
        BookCopy actual = bookCopy.orElse(BookCopy.builder().build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllBookCopiesList() {
        //given
        List<BookCopy> expected = findBookCopiesForFindAll();

        //when
        List<BookCopy> actual = bookCopyRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedBookCopy() {
        //given
        BookCopy expected = BookCopy.builder().id(6L).status("AVAILABLE").registrationDate(LocalDate.of(2000, 1, 1)).imagePath("image path").pricePerDay(13).bookDamages(new HashSet<>()).orders(new HashSet<>()).book(Book.builder().id(1L).authors(new HashSet<>()).genres(new HashSet<>()).build()).deleteStatus("EXISTS").build();
        BookCopy actual = BookCopy.builder().status("AVAILABLE").registrationDate(LocalDate.of(2000, 1, 1)).imagePath("image path").pricePerDay(13).bookDamages(new HashSet<>()).orders(new HashSet<>()).book(Book.builder().id(1L).authors(new HashSet<>()).genres(new HashSet<>()).build()).deleteStatus("EXISTS").build();

        //when
        actual = bookCopyRepository.save(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, bookCopyRepository.findById(expected.getId()).get());
    }

    @Test
    void updateTest_shouldUpdateBookCopy() {
        //given
        BookCopy bookCopy = BookCopy.builder().id(2L).status("AVAILABLE").registrationDate(LocalDate.of(2000, 1, 1)).imagePath("image path").pricePerDay(13).bookDamages(new HashSet<>()).orders(new HashSet<>()).book(Book.builder().id(1L).authors(new HashSet<>()).genres(new HashSet<>()).build()).deleteStatus("EXISTS").build();

        // when
        bookCopyRepository.saveAndFlush(bookCopy);
        Optional<BookCopy> foundBookCopy = bookCopyRepository.findById(bookCopy.getId());

        //then
        Assertions.assertTrue(foundBookCopy.isPresent());
        Assertions.assertEquals(bookCopy, bookCopyRepository.findById(bookCopy.getId()).get());
    }
}