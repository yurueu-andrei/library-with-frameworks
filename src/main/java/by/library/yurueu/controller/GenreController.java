package by.library.yurueu.controller;

import by.library.yurueu.dto.GenreDto;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/genres")
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/{id}")
    public GenreDto findById(@PathVariable Long id) throws ServiceException {
        return genreService.findById(id);
    }

    @GetMapping
    public List<GenreDto> findAll() throws ServiceException {
        return genreService.findAll();
    }
}