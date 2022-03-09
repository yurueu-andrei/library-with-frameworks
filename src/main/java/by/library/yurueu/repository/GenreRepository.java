package by.library.yurueu.repository;

import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.Genre;

import java.util.Set;

public interface GenreRepository extends BaseRepository<Genre> {
    Set<Book> findBooksByGenreId(Long id);
}