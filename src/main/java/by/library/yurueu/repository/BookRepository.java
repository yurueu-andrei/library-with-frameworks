package by.library.yurueu.repository;

import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;
import by.library.yurueu.entity.Genre;

import java.util.Set;

public interface BookRepository extends BaseRepository<Book> {
    Set<Author> findAuthorsByAuthorsId(Set<Long> authorsId);
    Set<Genre> findGenresByGenresId(Set<Long> genresId);
}