package by.library.yurueu.repository.impl;

import by.library.yurueu.entity.Genre;
import by.library.yurueu.exception.RepositoryException;
import by.library.yurueu.repository.BaseRepositoryTest;
import by.library.yurueu.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GenreRepositoryImplTest extends BaseRepositoryTest {
    private final GenreRepository genreRepository;

    public GenreRepositoryImplTest() {
        genreRepository = new GenreRepositoryImpl();
    }

    @Test
    public void findByIdTest_shouldReturnTheFirstGenreInDB() throws RepositoryException {
        //given
        Genre expected = findGenreForFindById();

        //when
        Genre actual = genreRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllGenresList() throws RepositoryException {
        //given
        List<Genre> expected = findGenresForFindAll();

        //when
        List<Genre> actual = genreRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedGenre() throws RepositoryException {
        //given
        Genre expected = Genre.builder().id(8L).genreName("tale").build();
        Genre actual = Genre.builder().genreName("tale").build();

        //when
        actual = genreRepository.add(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, genreRepository.findById(expected.getId()));
    }

    @Test
    void updateTest_shouldUpdateGenre() throws RepositoryException {
        //given
        Genre genre = Genre.builder().id(2L).genreName("tale").build();

        // when
        boolean isUpdated = genreRepository.update(genre);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(genre, genreRepository.findById(genre.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteGenre() throws RepositoryException {
        //given
        Long genreId = 1L;

        // when
        boolean isDeleted = genreRepository.delete(genreId);

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertThrows(RepositoryException.class, () -> genreRepository.findById(genreId));
    }
}