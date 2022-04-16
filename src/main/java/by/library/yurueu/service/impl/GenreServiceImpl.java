package by.library.yurueu.service.impl;

import by.library.yurueu.converter.GenreConverter;
import by.library.yurueu.dto.GenreDto;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.GenreRepository;
import by.library.yurueu.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public GenreDto findById(Long id) throws ServiceException {
        return genreRepository.findById(id).map(GenreConverter::toListDTO)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> findAll() throws ServiceException {
        try {
            return GenreConverter.toListDTO(genreRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName() + "s", "were not found"));
        }
    }
}