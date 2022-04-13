package by.library.yurueu.controller;

import by.library.yurueu.dto.BookSaveDto;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public BookSaveDto add(@RequestBody BookSaveDto bookSaveDto) throws ServiceException {
        return bookService.add(bookSaveDto);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return bookService.delete(id);
    }
}