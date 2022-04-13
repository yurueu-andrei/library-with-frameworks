package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.Genre;
import by.library.yurueu.repository.BaseRepositoryTest;
import by.library.yurueu.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class GenreRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstGenreInDB() {
        //given
        Genre expected = findGenreForFindById();

        //when
        Optional<Genre> genre = genreRepository.findById(expected.getId());
        Genre actual = genre.orElse(Genre.builder().build());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllGenresList() {
        //given
        List<Genre> expected = findGenresForFindAll();

        //when
        List<Genre> actual = genreRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedGenre() {
        //given
        Genre expected = Genre.builder().id(8L).genreName("tale").books(new HashSet<>(){{add(Book.builder().id(1L).bookCopies(new HashSet<>()).build());}}).deleteStatus("EXISTS").build();
        Genre actual = Genre.builder().genreName("tale").books(new HashSet<>(){{add(Book.builder().id(1L).bookCopies(new HashSet<>()).build());}}).deleteStatus("EXISTS").build();

        //when
        actual = genreRepository.saveAndFlush(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, genreRepository.findById(expected.getId()).get());
    }

    @Test
    void updateTest_shouldUpdateGenre() {
        //given
        Genre genre = Genre.builder().id(2L).genreName("tale").books(new HashSet<>(){{add(Book.builder().id(1L).bookCopies(new HashSet<>()).build());}}).deleteStatus("EXISTS").build();

        // when
        genreRepository.saveAndFlush(genre);
        Optional<Genre> foundGenre = genreRepository.findById(genre.getId());

        //then
        Assertions.assertTrue(foundGenre.isPresent());
        Assertions.assertEquals(genre, genreRepository.findById(genre.getId()).get());
    }
}