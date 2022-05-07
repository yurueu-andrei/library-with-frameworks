package by.library.yurueu.service.impl;

import by.library.yurueu.dto.BookSaveDto;
import by.library.yurueu.entity.Book;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.mapper.BookMapper;
import by.library.yurueu.repository.BookCopyRepository;
import by.library.yurueu.repository.BookDamageRepository;
import by.library.yurueu.repository.BookRepository;
import by.library.yurueu.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BookDamageRepository bookDamageRepository;
    private final BookMapper bookMapper;

    @Transactional
    @Override
    public BookSaveDto add(BookSaveDto bookSaveDto) throws ServiceException {
        try {
            Book book = bookMapper.fromSaveDTO(bookSaveDto);
            book.setStatus("ACTIVE");
            return bookMapper.toSaveDTO(bookRepository.save(book));
        } catch (Exception ex) {
            throw new ServiceException(String.format("The book was not saved. %s", bookSaveDto), ex);
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The book was not deleted. The book was not found. id = %d", id)));
        try {
            deleteLinks(book);
            book.setStatus("DELETED");
            bookRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The book was not deleted. id = %d", id), ex);
        }
    }

    private void deleteLinks(Book book) {
        book.getBookCopies().forEach(bookCopy -> {
            bookCopy.setStatus("DELETED");
            bookCopyRepository.save(bookCopy);

            bookCopy.getBookDamages().forEach(bookDamage -> {
                bookDamage.setStatus("DELETED");
                bookDamageRepository.save(bookDamage);
            });
        });
    }
}