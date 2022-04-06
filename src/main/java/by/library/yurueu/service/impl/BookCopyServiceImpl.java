package by.library.yurueu.service.impl;

import by.library.yurueu.converter.BookCopyConverter;
import by.library.yurueu.dto.BookCopyDto;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookCopySaveDto;
import by.library.yurueu.dto.BookCopyUpdateDto;
import by.library.yurueu.entity.BookCopy;
import by.library.yurueu.exception.ServiceException;
import by.library.yurueu.repository.BookCopyRepository;
import by.library.yurueu.service.BookCopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookCopyServiceImpl implements BookCopyService {
    private final BookCopyRepository bookCopyRepository;

    @Override
    public BookCopyDto findById(Long id) throws ServiceException {
        return bookCopyRepository.findById(id).map(BookCopyConverter::toDTO)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookCopyListDto> findAll() throws ServiceException {
        try {
            return BookCopyConverter.toListDTO(bookCopyRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName() + "s", "were not found"));
        }
    }

    @Transactional
    @Override
    public BookCopySaveDto add(BookCopySaveDto bookCopySaveDto) throws ServiceException {
        try {
            BookCopy bookCopy = BookCopyConverter.fromSaveDTO(bookCopySaveDto);
            return BookCopyConverter.toSaveDTO(bookCopyRepository.save(bookCopy));
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not added"));
        }
    }

    @Transactional
    @Override
    public boolean update(BookCopyUpdateDto bookCopyUpdateDto) throws ServiceException {
        try {
            BookCopy bookCopy = BookCopyConverter.fromUpdateDTO(bookCopyUpdateDto);
            bookCopyRepository.save(bookCopy);
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not updated"));
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            Optional<BookCopy> bookCopy = bookCopyRepository.findById(id);
            bookCopyRepository.delete(bookCopy.get());
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not deleted"));
        }
    }
}