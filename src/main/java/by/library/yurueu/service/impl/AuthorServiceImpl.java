package by.library.yurueu.service.impl;

import by.library.yurueu.converter.AuthorConverter;
import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveAndUpdateDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.AuthorRepository;
import by.library.yurueu.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto findById(Long id) throws ServiceException {
        return authorRepository.findById(id).map(AuthorConverter::toDTO)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthorListDto> findAll() throws ServiceException {
        try {
            return AuthorConverter.toListDTO(authorRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName() + "s", "were not found"));
        }
    }

    @Transactional
    @Override
    public AuthorSaveAndUpdateDto add(AuthorSaveAndUpdateDto authorSaveAndUpdateDto) throws ServiceException {
        try {
            Author author = AuthorConverter.fromSaveDTO(authorSaveAndUpdateDto);
            author.setStatus("ACTIVE");
            return AuthorConverter.toSaveDTO(authorRepository.save(author));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not added"));
        }
    }

    @Transactional
    @Override
    public boolean update(AuthorSaveAndUpdateDto authorUpdateDto) throws ServiceException {
        try {
            Author author = AuthorConverter.fromUpdateDTO(authorUpdateDto);
            author.setStatus("ACTIVE");
            authorRepository.save(author);
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not updated"));
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        Optional<Author> authorToDelete = authorRepository.findById(id);
        if (authorToDelete.isPresent()) {
            Author author = authorToDelete.get();
            author.setStatus("DELETED");
            authorRepository.save(author);
            return true;
        }
        throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not deleted"));
    }
}