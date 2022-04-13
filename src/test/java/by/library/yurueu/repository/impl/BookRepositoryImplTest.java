package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Book;
import by.library.yurueu.repository.BaseRepositoryTest;
import by.library.yurueu.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class BookRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstBookInDB() {
        //given
        Book expected = findBookForFindById();

        //when
        Optional<Book> book = bookRepository.findById(expected.getId());
        Book actual = book.orElse(Book.builder().build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllBooksList() {
        //given
        List<Book> expected = findBooksForFindAll();

        //when
        List<Book> actual = bookRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedBook() {
        //given
        Book expected = Book.builder().id(6L).title("asd").pagesNumber(12).imagePath("image path").genres(new HashSet<>()).authors(new HashSet<>()).bookCopies(new HashSet<>()).deleteStatus("EXISTS").build();
        Book actual = Book.builder().title("asd").pagesNumber(12).imagePath("image path").genres(new HashSet<>()).authors(new HashSet<>()).bookCopies(new HashSet<>()).deleteStatus("EXISTS").build();

        //when
        actual = bookRepository.saveAndFlush(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, bookRepository.findById(expected.getId()).get());
    }

    @Test
    void updateTest_shouldUpdateBook() {
        //given
        Book book = Book.builder().id(2L).title("Hello").pagesNumber(12).imagePath("image path").genres(new HashSet<>()).authors(new HashSet<>()).bookCopies(new HashSet<>()).deleteStatus("EXISTS").build();

        // when
        bookRepository.saveAndFlush(book);
        Optional<Book> foundBook = bookRepository.findById(book.getId());

        //then
        Assertions.assertTrue(foundBook.isPresent());
        Assertions.assertEquals(book, bookRepository.findById(book.getId()).get());
    }
}