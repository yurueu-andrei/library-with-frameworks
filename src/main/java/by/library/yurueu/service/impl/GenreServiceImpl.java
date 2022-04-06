package by.library.yurueu.service.impl;

import by.library.yurueu.converter.GenreConverter;
import by.library.yurueu.dto.GenreListDto;
import by.library.yurueu.dto.GenreUpdateDto;
import by.library.yurueu.entity.Genre;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.GenreRepository;
import by.library.yurueu.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public GenreListDto findById(Long id) throws ServiceException {
        return genreRepository.findById(id).map(GenreConverter::toListDTO)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<GenreListDto> findAll() throws ServiceException {
        try {
            return GenreConverter.toListDTO(genreRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName() + "s", "were not found"));
        }
    }

    @Transactional
    @Override
    public GenreListDto add(GenreListDto genreListDto) throws ServiceException {
        try {
            Genre genre = GenreConverter.fromListDTO(genreListDto);
            return GenreConverter.toListDTO(genreRepository.save(genre));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not added"));
        }
    }

    @Transactional
    @Override
    public boolean update(GenreUpdateDto genreUpdateDto) throws ServiceException {
        try {
            Genre genre = GenreConverter.fromUpdateDTO(genreUpdateDto);
            genreRepository.save(genre);
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not updated"));
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            Optional<Genre> genre = genreRepository.findById(id);
            genreRepository.delete(genre.get());
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not deleted"));
        }
    }
}