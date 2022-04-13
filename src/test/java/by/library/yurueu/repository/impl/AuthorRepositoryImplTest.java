package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.repository.AuthorRepository;
import by.library.yurueu.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstAuthorInDB() {
        //given
        Author expected = findAuthorForFindById();

        //when
        Optional<Author> author = authorRepository.findById(expected.getId());
        Author actual = author.orElse(Author.builder().build());
        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_shouldReturnAllAuthorsList() {
        //given
        List<Author> expected = findAuthorsForFindAll();

        //when
        List<Author> actual = authorRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void addTest_shouldReturnAddedAuthor() {
        //given
        Author expected = Author.builder().id(6L).firstName("Mikhail").lastName("Lermontov").birthDate(LocalDate.of(1999, 8, 8)).imagePath("image path").deleteStatus("EXISTS").books(new HashSet<>()).books(new HashSet<>(){{add(Book.builder().bookCopies(new HashSet<>()).id(1L).build());}}).build();
        Author actual = Author.builder().firstName("Mikhail").lastName("Lermontov").birthDate(LocalDate.of(1999, 8, 8)).imagePath("image path").deleteStatus("EXISTS").books(new HashSet<>(){{add(Book.builder().bookCopies(new HashSet<>()).id(1L).build());}}).build();
        //when
        actual = authorRepository.save(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, authorRepository.findById(expected.getId()).get());
    }

    @Test
    public void updateTest_shouldUpdateAuthor() {
        //given
        Author author = Author.builder().id(2L).firstName("Mikhail").lastName("Lermontov").birthDate(LocalDate.of(1998, 8, 8)).imagePath("image path").deleteStatus("EXISTS").books(new HashSet<>()).books(new HashSet<>(){{add(Book.builder().bookCopies(new HashSet<>()).id(1L).build());}}).build();

        // when
        authorRepository.save(author);
        Optional<Author> foundAuthor = authorRepository.findById(author.getId());

        //then
        Assertions.assertTrue(foundAuthor.isPresent());
        Assertions.assertEquals(author, authorRepository.findById(author.getId()).get());
    }
}