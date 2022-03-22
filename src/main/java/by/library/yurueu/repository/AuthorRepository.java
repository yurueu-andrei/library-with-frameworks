package by.library.yurueu.repository;

import by.library.yurueu.entity.Author;
import by.library.yurueu.entity.Book;

import java.util.Set;

public interface AuthorRepository extends BaseRepository<Author>{
    Set<Book> findBooksByAuthorId(Long id);
}