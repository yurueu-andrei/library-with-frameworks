package by.library.yurueu.controller;

import by.library.yurueu.dto.AuthorDto;
import by.library.yurueu.dto.AuthorListDto;
import by.library.yurueu.dto.AuthorSaveAndUpdateDto;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping(value = "/authors")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/{id}")
    public AuthorDto findById(@PathVariable Long id) throws ServiceException {
        return authorService.findById(id);
    }

    @GetMapping
    public List<AuthorListDto> findAll() throws ServiceException {
        return authorService.findAll();
    }

    @PreAuthorize("hasAuthority({'AUTHOR_WRITE'})")
    @PostMapping
    public AuthorSaveAndUpdateDto add(
            @RequestBody AuthorSaveAndUpdateDto authorSaveAndUpdateDto
    ) throws ServiceException {
        return authorService.add(authorSaveAndUpdateDto);
    }

    @PreAuthorize("hasAuthority({'AUTHOR_WRITE'})")
    @PutMapping
    public AuthorSaveAndUpdateDto update(
            @RequestBody AuthorSaveAndUpdateDto authorUpdateDto
    ) throws ServiceException {
        authorService.update(authorUpdateDto);
        return authorUpdateDto;
    }

    @PreAuthorize("hasAuthority({'AUTHOR_DELETE'})")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return authorService.delete(id);
    }
}