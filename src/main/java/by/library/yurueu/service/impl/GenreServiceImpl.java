package by.library.yurueu.service.impl;

import by.library.yurueu.converter.GenreConverter;
import by.library.yurueu.dto.GenreListDto;
import by.library.yurueu.dto.GenreUpdateDto;
import by.library.yurueu.entity.Genre;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.GenreRepository;
import by.library.yurueu.service.GenreService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public GenreListDto findById(Long id) throws ServiceException {
        try {
            Genre genre = genreRepository.findById(id);
            return GenreConverter.toListDTO(genre);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public List<GenreListDto> findAll() throws ServiceException {
        try {
            List<Genre> genres = genreRepository.findAll();
            return GenreConverter.toListDTO(genres);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public GenreListDto add(GenreListDto genreListDto) throws ServiceException {
        try {
            Genre genre = GenreConverter.fromListDTO(genreListDto);
            return GenreConverter.toListDTO(genreRepository.add(genre));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean update(GenreUpdateDto genreUpdateDto) throws ServiceException {
        try {
            Genre genre = GenreConverter.fromUpdateDTO(genreUpdateDto);
            return genreRepository.update(genre);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return genreRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }
}