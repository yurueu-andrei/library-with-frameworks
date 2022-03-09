package by.library.yurueu.service.impl;

import by.library.yurueu.converter.AuthorConverter;
import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveDto;
import by.library.yurueu.dto.AuthorUpdateDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.AuthorRepository;
import by.library.yurueu.repository.impl.AuthorRepositoryImpl;
import by.library.yurueu.service.AuthorService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl() {
        authorRepository = new AuthorRepositoryImpl();
    }

    @Override
    public AuthorDto findById(Long id) throws ServiceException {
        try {
            Author author = authorRepository.findById(id);
            author.setBooks(authorRepository.findBooksByAuthorId(id));
            return AuthorConverter.toDTO(author);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public Set<AuthorListDto> findAll() throws ServiceException {
        try {
            List<Author> authors = authorRepository.findAll();
            return authors.stream()
                    .map(AuthorConverter::toListDTO)
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public AuthorSaveDto add(AuthorSaveDto authorSaveDto) throws ServiceException {
        try {
            Author author = AuthorConverter.fromSaveDTO(authorSaveDto);
            return AuthorConverter.toSaveDTO(authorRepository.add(author));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean update(AuthorUpdateDto authorUpdateDto) throws ServiceException {
        try {
            Author author = AuthorConverter.fromUpdateDTO(authorUpdateDto);
            return authorRepository.update(author);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return authorRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }
}