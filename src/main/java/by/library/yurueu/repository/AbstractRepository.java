package by.library.yurueu.repository;

import by.library.yurueu.exception.RepositoryException;

import java.util.List;

public interface AbstractRepository<E> {
    E findById(Long id) throws RepositoryException;
    List<E> findAll() throws RepositoryException;
    E add(E element) throws RepositoryException;
    boolean update(E element) throws RepositoryException;
    boolean delete(Long id) throws RepositoryException;
}
