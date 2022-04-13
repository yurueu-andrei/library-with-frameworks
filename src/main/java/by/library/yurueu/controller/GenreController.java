package by.library.yurueu.controller;

import by.library.yurueu.dto.GenreListDto;
import by.library.yurueu.dto.GenreSaveDto;
import by.library.yurueu.dto.GenreUpdateDto;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/genres")
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/{id}")
    public GenreListDto findById(@PathVariable Long id) throws ServiceException {
        return genreService.findById(id);
    }

    @GetMapping
    public List<GenreListDto> findAll() throws ServiceException {
        return genreService.findAll();
    }

    @PostMapping
    public GenreSaveDto add(@RequestBody GenreSaveDto genreSaveDto) throws ServiceException {
        return genreService.add(genreSaveDto);
    }

    @PutMapping
    public GenreUpdateDto update(@RequestBody GenreUpdateDto genreUpdateDto) throws ServiceException {
        genreService.update(genreUpdateDto);
        return genreUpdateDto;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return genreService.delete(id);
    }
}