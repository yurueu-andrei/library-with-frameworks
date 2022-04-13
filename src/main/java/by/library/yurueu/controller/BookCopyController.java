package by.library.yurueu.controller;

import by.library.yurueu.dto.BookCopyDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookCopySaveAndUpdateDto;
import by.library.yurueu.dto.BookSaveDto;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.service.BookCopyService;
import by.library.yurueu.service.BookService;
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
@RequestMapping(value = "/books")
public class BookCopyController {
    private final BookService bookService;
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
    public BookSaveDto add(
            @RequestBody BookSaveDto bookSaveDto
    ) throws ServiceException {
        return bookService.add(bookSaveDto);
    }

    @PostMapping("/copies")
    public BookCopySaveAndUpdateDto add(
            @RequestBody BookCopySaveAndUpdateDto bookCopySaveAndUpdateDto
    ) throws ServiceException {
        return bookCopyService.add(bookCopySaveAndUpdateDto);
    }

    @PutMapping
    public BookCopySaveAndUpdateDto update(
            @RequestBody BookCopySaveAndUpdateDto bookCopyUpdateDto
    ) throws ServiceException {
        bookCopyService.update(bookCopyUpdateDto);
        return bookCopyUpdateDto;
    }

    @DeleteMapping("/{id}")
    public boolean deleteBook(@PathVariable Long id) throws ServiceException {
        return bookService.delete(id);
    }

    @DeleteMapping("/copies/{id}")
    public boolean deleteCopy(@PathVariable Long id) throws ServiceException {
        return bookCopyService.delete(id);
    }
}