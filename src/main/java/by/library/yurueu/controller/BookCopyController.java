package by.library.yurueu.controller;

import by.library.yurueu.dto.BookCopyDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookCopySaveDto;
import by.library.yurueu.dto.BookCopyUpdateDto;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.service.BookCopyService;
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
@RequestMapping(value = "/book-copies")
public class BookCopyController {
    private final BookCopyService bookCopyService;

    @GetMapping("/{id}")
    public BookCopyDto findById(@PathVariable Long id) throws ServiceException {
        return bookCopyService.findById(id);
    }

    @GetMapping
    public List<BookCopyListDto> findAll() throws ServiceException {
        return bookCopyService.findAll();
    }

    @PostMapping
    public BookCopySaveDto add(@RequestBody BookCopySaveDto bookCopySaveDto) throws ServiceException {
        return bookCopyService.add(bookCopySaveDto);
    }

    @PutMapping
    public BookCopyUpdateDto update(@RequestBody BookCopyUpdateDto bookCopyUpdateDto) throws ServiceException {
        bookCopyService.update(bookCopyUpdateDto);
        return bookCopyUpdateDto;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return bookCopyService.delete(id);
    }
}