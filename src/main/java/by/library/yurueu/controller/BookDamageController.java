package by.library.yurueu.controller;

import by.library.yurueu.dto.BookDamageDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.BookDamageSaveDto;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.service.BookDamageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/book-damages")
public class BookDamageController {
    private final BookDamageService bookDamageService;

    @GetMapping("/{id}")
    public BookDamageDto findById(@PathVariable Long id) throws ServiceException {
        return bookDamageService.findById(id);
    }

    @GetMapping
    public List<BookDamageListDto> findAll() throws ServiceException {
        return bookDamageService.findAll();
    }

    @PostMapping
    public BookDamageSaveDto add(@RequestBody BookDamageSaveDto bookDamageSaveDto) throws ServiceException {
        return bookDamageService.add(bookDamageSaveDto);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return bookDamageService.delete(id);
    }
}