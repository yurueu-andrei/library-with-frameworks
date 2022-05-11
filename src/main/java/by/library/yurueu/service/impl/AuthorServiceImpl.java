package by.library.yurueu.service.impl;

import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveAndUpdateDto;
import by.library.yurueu.entity.Author;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.mapper.AuthorMapper;
import by.library.yurueu.repository.AuthorRepository;
import by.library.yurueu.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorDto findById(Long id) throws ServiceException {
        return authorRepository.findById(id).map(authorMapper::toDTO)
                .orElseThrow(() -> new ServiceException(String.format("The author was not found. id = %d", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthorListDto> findAll() throws ServiceException {
        try {
            return authorMapper.toListDto(authorRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException("The authors were not found", ex);
        }
    }

    @Transactional
    @Override
    public AuthorSaveAndUpdateDto add(AuthorSaveAndUpdateDto authorSaveAndUpdateDto) throws ServiceException {
        try {
            Author author = authorMapper.fromSaveOrUpdateDTO(authorSaveAndUpdateDto);
            author.setStatus("ACTIVE");
            return authorMapper.toSaveDTO(authorRepository.save(author));
        } catch (Exception ex) {
            throw new ServiceException(String.format("The author was not saved. %s", authorSaveAndUpdateDto), ex);
        }
    }

    @Transactional
    @Override
    public boolean update(AuthorSaveAndUpdateDto authorSaveAndUpdateDto) throws ServiceException {
        Author author = authorRepository.findById(authorSaveAndUpdateDto.getId())
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The author was not updated. The author was not found. id = %d",
                                        authorSaveAndUpdateDto.getId())));
        try {
            settingUpdatedFields(author, authorSaveAndUpdateDto);
            authorRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The author was not updated. %s", authorSaveAndUpdateDto), ex);
        }
    }

    private void settingUpdatedFields(Author author, AuthorSaveAndUpdateDto authorSaveAndUpdateDto) {
        if (authorSaveAndUpdateDto.getFirstName() != null) {
            author.setFirstName(authorSaveAndUpdateDto.getFirstName());
        }
        if (authorSaveAndUpdateDto.getLastName() != null) {
            author.setLastName(authorSaveAndUpdateDto.getLastName());
        }
        if (authorSaveAndUpdateDto.getBirthDate() != null) {
            author.setBirthDate(authorSaveAndUpdateDto.getBirthDate());
        }
        if (authorSaveAndUpdateDto.getImagePath() != null) {
            author.setImagePath(authorSaveAndUpdateDto.getImagePath());
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The author was not deleted. The author was not found. id = %d", id)));
        try {
            author.setStatus("DELETED");
            authorRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The author was not deleted. id = %d", id), ex);
        }
    }
}