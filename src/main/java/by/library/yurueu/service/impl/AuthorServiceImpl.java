package by.library.yurueu.service.impl;

import by.library.yurueu.converter.AuthorConverter;
import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveDto;
import by.library.yurueu.dto.AuthorUpdateDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.AuthorRepository;
import by.library.yurueu.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

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
    public List<AuthorListDto> findAll() throws ServiceException {
        try {
            List<Author> authors = authorRepository.findAll();
            return AuthorConverter.toListDTO(authors);
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